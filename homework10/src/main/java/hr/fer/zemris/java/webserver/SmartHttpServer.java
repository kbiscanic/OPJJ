package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Simple HTTP server.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class SmartHttpServer {

	/**
	 * IP address on which this server should listen for connections.
	 */
	private String address;
	/**
	 * Port on which this server should listen for connections.
	 */
	private int port;
	/**
	 * Maximal number of threads in thread pool used to serve clients requests.
	 */
	private int workerThreads;
	/**
	 * Timeout value (in seconds) for a single session.
	 */
	private int sessionTimeout;
	/**
	 * Map of all supported extension and mime types associated with them.
	 */
	private Map<String, String> mimeTypes = new HashMap<>();
	/**
	 * Map of all supported worker threads and references to objects associated
	 * with them.
	 */
	private Map<String, IWebWorker> workersMap = new HashMap<>();
	/**
	 * Thread listening for incoming connections and forwarding them to client
	 * threads.
	 */
	private ServerThread serverThread;
	/**
	 * Thread pool used to execute clients requests.
	 */
	private ExecutorService threadPool;
	/**
	 * Path to document root of the server.
	 */
	private Path documentRoot;
	/**
	 * Map of all currently active sessions.
	 */
	private Map<String, SessionMapEntry> sessions = new ConcurrentHashMap<>();
	/**
	 * Random generator used to generate random SID.
	 */
	private Random sessionRandom = new Random();
	/**
	 * Thread used to collect expired sessions.
	 */
	private SessionGarbageCollector garbageCollector;

	/**
	 * Private constructor for {@link SmartHttpServer} server. Loads
	 * configurations for server.config and associated config files and starts
	 * server.
	 * 
	 * @param configFileName
	 *            Path to server.config file.
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private SmartHttpServer(String configFileName)
			throws FileNotFoundException, IOException, InterruptedException {
		Properties config = new Properties();

		FileInputStream conffs = null;
		try {
			conffs = new FileInputStream(configFileName);
			config.load(conffs);
		} catch (IOException ignore) {
		} finally {
			if (conffs != null) {
				conffs.close();
			}
		}

		address = config.getProperty("server.address");
		port = Integer.parseInt(config.getProperty("server.port"));
		workerThreads = Integer.parseInt(config
				.getProperty("server.workerThreads"));
		sessionTimeout = Integer
				.parseInt(config.getProperty("session.timeout"));
		documentRoot = Paths.get(config.getProperty("server.documentRoot"));

		Properties mimes = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(config.getProperty("server.mimeConfig"));
			mimes.load(fis);
		} catch (IOException ignore) {
		} finally {
			if (fis != null) {
				fis.close();
			}
		}

		for (Entry<Object, Object> e : mimes.entrySet()) {
			mimeTypes.put((String) e.getKey(), (String) e.getValue());
		}

		Properties workers = new Properties();

		try {
			fis = new FileInputStream(config.getProperty("server.workers"));
			workers.load(fis);
		} catch (IOException ignore) {
		} finally {
			if (fis != null) {
				fis.close();
			}
		}

		for (Entry<Object, Object> e : workers.entrySet()) {
			try {
				Class<?> referenceToClass = this.getClass().getClassLoader()
						.loadClass((String) e.getValue());
				IWebWorker iww = (IWebWorker) referenceToClass.newInstance();
				workersMap.put((String) e.getKey(), iww);
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException cnfe) {
				System.err.println("Worker unavailable.");
			}
		}
		start();
	}

	/**
	 * Main method for this program. Checks if number of arguments is OK and
	 * constructs new {@link SmartHttpServer}. Otherwise, quits.
	 * 
	 * @param args
	 *            1 argument expected, path to server.config file.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err
					.println("This program must be called with config.properties path as argument.");
		}
		try {
			new SmartHttpServer(args[0]);
		} catch (Exception e) {
			System.err
					.println("Error occured. Please check your properties files for errors.");
		}
	}

	/**
	 * Method used to start server. Starts one {@link ServerThread}, one
	 * {@link SessionGarbageCollector} and Executor thread pool for doing client
	 * tasks.
	 */
	protected synchronized void start() {
		if (threadPool == null) {
			threadPool = Executors.newFixedThreadPool(workerThreads);
		}
		if (serverThread == null) {
			serverThread = new ServerThread();
			serverThread.start();
		}
		if (garbageCollector == null) {
			garbageCollector = new SessionGarbageCollector();
			garbageCollector.start();
		}

	}

	/**
	 * Method used to shut down our server. Stops {@link ServerThread} and shuts
	 * down thread pool.
	 */
	protected synchronized void stop() {
		try {
			if (serverThread != null) {
				serverThread.serverSocket.close();
			}
			if (threadPool != null) {
				threadPool.shutdown();
			}
			threadPool = null;
			serverThread = null;
		} catch (IOException ignorable) {
		}
	}

	/**
	 * Private thread used to clean up expired sessions every 5 minutes. This
	 * thread is daemon.
	 * 
	 * @author Kristijan Biscanic
	 * 
	 */
	private class SessionGarbageCollector extends Thread {

		/**
		 * Default constructor. Sets thread to daemon so it won't prevent
		 * application shut down.
		 */
		public SessionGarbageCollector() {
			this.setDaemon(true);
		}

		@Override
		public void run() {
			while (true) {
				try {
					sleep(1000 * 60 * 5);
				} catch (InterruptedException e1) {
				}

				for (Entry<String, SessionMapEntry> e : sessions.entrySet()) {
					if (e.getValue().validUntil < new Date().getTime()) {
						sessions.remove(e.getKey());
					}
				}

			}
		}

	}

	/**
	 * Thread running the actual server, creating {@link ServerSocket} and
	 * listening to incoming connections. It simply forwards requests to thread
	 * pool for execution.
	 * 
	 * @author Kristijan Biscanic
	 * 
	 */
	protected class ServerThread extends Thread {

		/**
		 * {@link ServerSocket} on which this thread is listening for incoming
		 * connections.
		 */
		protected ServerSocket serverSocket;

		@Override
		public void run() {
			synchronized (SmartHttpServer.this) {
				try {
					serverSocket = new ServerSocket(port, 0,
							InetAddress.getByName(address));
					while (true) {
						Socket client = serverSocket.accept();
						ClientWorker cw = new ClientWorker(client);
						threadPool.submit(cw);
					}
				} catch (IOException e) {
					System.err.println("Closing server thread.");
				}
			}
		}
	}

	/**
	 * This is runnable target that is intended to execute whenever server
	 * thread receives request from client. It only accepts GET requests.
	 * 
	 * @author Kristijan Biscanic
	 * 
	 */
	private class ClientWorker implements Runnable {
		/**
		 * Socket representing connection to client.
		 */
		private Socket csocket;
		/**
		 * Input stream used to receive request from client.
		 */
		private PushbackInputStream istream;
		/**
		 * Output stream used to send reply to client.
		 */
		private OutputStream ostream;
		/**
		 * Version of protocol used by client.
		 */
		private String version;
		/**
		 * Method of request.
		 */
		private String method;
		/**
		 * Parameters received with request.
		 */
		private Map<String, String> params = new HashMap<>();
		/**
		 * Permanent parameters associated with this request.
		 */
		private Map<String, String> permParams = null;
		/**
		 * List of cookies that must be included into our reply.
		 */
		private List<RCCookie> outputCookies = new ArrayList<>();

		/**
		 * Default constructor.
		 * 
		 * @param csocket
		 *            Connection to the client.
		 */
		public ClientWorker(Socket csocket) {
			this.csocket = csocket;
		}

		@Override
		public void run() {
			FileInputStream fis = null;
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();
				List<String> request = readRequest();
				if (request.size() < 1) {
					returnStatus(400, "Invalid request!");
				}
				String firstLine = request.get(0);
				String[] splitted = firstLine.split("\\s");
				method = splitted[0];
				String requestedPath = splitted[1];
				version = splitted[2];

				if (!method.equals("GET")
						|| (!version.equals("HTTP/1.1") && !version
								.equals("HTTP/1.0"))) {
					returnStatus(400, "Invalid request!");
					return;
				}

				String addr = null;
				if (request.get(1).startsWith("Host:")) {
					String[] spl = request.get(1).split(" ");
					if (spl[1].contains(":")) {
						addr = spl[1].substring(0, spl[1].indexOf(':'));
					} else {
						addr = spl[1];
					}
				} else {
					addr = address;
				}

				SessionMapEntry session = checkSession(request);
				outputCookies.add(new RCCookie("sid", session.sid, null, addr,
						"/"));
				permParams = session.map;

				splitted = requestedPath.split("\\?");
				String path = splitted[0];
				String paramString = null;
				if (splitted.length == 2) {
					paramString = splitted[1];
				}
				if (paramString != null) {
					parseParameters(paramString);
				}

				if (path.startsWith("/ext/")) {
					execute("hr.fer.zemris.java.webserver.workers."
							+ path.substring(5), new RequestContext(ostream,
							params, permParams, outputCookies));
				} else

				if (workersMap.containsKey(path)) {
					workersMap.get(path).processRequest(
							new RequestContext(ostream, params, permParams,
									outputCookies));
				} else {

					Path rPath = Paths.get(
							SmartHttpServer.this.documentRoot.toString(), path)
							.normalize();
					if (!rPath.toAbsolutePath().startsWith(
							documentRoot.toAbsolutePath())) {
						returnStatus(403, "Forbidden!");
						return;
					}
					if (!rPath.toFile().exists() || !rPath.toFile().canRead()) {
						returnStatus(404, "File not found!");
						return;
					}
					String extension = rPath
							.getFileName()
							.toString()
							.substring(
									rPath.getFileName().toString()
											.lastIndexOf('.') + 1);

					if (extension.equals("smscr")) {
						BufferedReader br = new BufferedReader(
								new InputStreamReader(new FileInputStream(rPath
										.toFile()), StandardCharsets.UTF_8));

						StringBuilder input = new StringBuilder();

						String line = null;
						while (true) {
							line = br.readLine();
							if (line == null) {
								break;
							}
							line = line.trim();
							input.append(line).append("\n");
						}
						br.close();
						String docBody = input.toString();

						new SmartScriptEngine(
								new SmartScriptParser(docBody)
										.getDocumentNode(),
								new RequestContext(ostream, params, permParams,
										outputCookies)).execute();
					} else {
						String mimeType = mimeTypes.get(extension);
						if (mimeType == null) {
							mimeType = "application/octet-stream";
						}
						RequestContext rc = new RequestContext(ostream, params,
								permParams, null);
						rc.setMimeType(mimeType);
						rc.generateHeader();

						fis = new FileInputStream(rPath.toFile());
						ByteArrayOutputStream bos = new ByteArrayOutputStream();

						byte[] data = new byte[1024];
						int read = fis.read(data, 0, 1024);
						while (read != -1) {
							bos.write(data, 0, read);
							read = fis.read(data, 0, 1024);

						}
						bos.flush();
						rc.write(bos.toByteArray());

						istream.close();
						ostream.close();
					}
				}

			} catch (IOException ioe) {
				System.err.println("IO Exception in client thread.");
			} finally {
				try {
					csocket.close();
				} catch (IOException ignorable) {
				}

				if (fis != null) {
					try {
						fis.close();
					} catch (IOException ignorable) {
					}
				}
			}

		}

		/**
		 * Method used to check whether there is session associated with this
		 * request. If there is, it simply renews its expiration time. If not,
		 * new session is created and new SID generated. This method always
		 * returns valid {@link SessionMapEntry} object.
		 * 
		 * @param lines
		 *            Request from client.
		 * @return {@link SessionMapEntry} for this session.
		 */
		private synchronized SessionMapEntry checkSession(List<String> lines) {
			String sidCandidate = null;
			SessionMapEntry temp = null;
			for (String s : lines) {
				if (!s.startsWith("Cookie:")) {
					continue;
				}
				String cookie = s.substring(7).trim();
				String[] splitted = cookie.split(";");
				for (String cook : splitted) {
					if (cook.startsWith("sid=")) {
						sidCandidate = cook.substring(5, cook.length() - 1);
						break;
					}
				}
				if (sidCandidate != null) {
					break;
				}
			}
			if (sidCandidate != null) {
				temp = sessions.get(sidCandidate);
			}
			if (sidCandidate == null || temp == null) {
				temp = new SessionMapEntry();
				String sid = null;
				do {
					sid = Long.toString(sessionRandom.nextLong());
				} while (sessions.containsKey(sid));
				temp.sid = sid;

			} else {
				if (temp.validUntil < new Date().getTime()) {
					sessions.remove(sidCandidate);
					temp = new SessionMapEntry();
					String sid = null;
					do {
						sid = Long.toString(sessionRandom.nextLong());
					} while (sessions.containsKey(sid));
					temp.sid = sid;
				}
			}
			temp.validUntil = new Date().getTime() + sessionTimeout * 1000;
			sessions.put(temp.sid, temp);

			return temp;
		}

		/**
		 * Method used to parse parameters from the client request.
		 * 
		 * @param paramString
		 *            String containing all parameters.
		 */
		private void parseParameters(String paramString) {
			String[] splitted = paramString.split("&");
			for (String param : splitted) {
				String[] splitted2 = param.split("=");
				if (splitted2.length != 2) {
					System.err.println("Invalid parameter");
					return;
				}
				params.put(splitted2[0], splitted2[1]);
			}
		}

		/**
		 * Method used to generate and return status code and status text to
		 * client.
		 * 
		 * @param code
		 *            Status code.
		 * @param text
		 *            Status text.
		 * @throws IOException
		 */
		private void returnStatus(int code, String text) throws IOException {
			RequestContext rc = new RequestContext(ostream, null, null, null);
			rc.setStatusCode(code);
			rc.setStatusText(text);
			rc.generateHeader();
		}

		/**
		 * Method used to read request and save its lines into list.
		 * 
		 * @return List of lines of request.
		 * @throws IOException
		 */
		private List<String> readRequest() throws IOException {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					istream, StandardCharsets.ISO_8859_1));
			List<String> header = new ArrayList<>();
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				line = line.trim();
				if (line.isEmpty()) {
					break;
				}
				header.add(line);
			}
			return header;
		}
	}

	/**
	 * Method used to execute given path associated with {@link IWebWorker}.
	 * 
	 * @param workerName
	 *            Name of worker that has to be executed.
	 * @param context
	 *            Context for this request.
	 */
	public synchronized void execute(String workerName, RequestContext context) {
		try {
			Class<?> referenceToClass = this.getClass().getClassLoader()
					.loadClass(workerName);
			IWebWorker iww = (IWebWorker) referenceToClass.newInstance();
			iww.processRequest(context);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException cnfe) {
			System.err.println("Worker unavailable.");

		}
	}

	/**
	 * Map entry representing current session and containing permanent
	 * parameters for that session.
	 * 
	 * @author Kristijan Biscanic
	 * 
	 */
	private static class SessionMapEntry {
		/**
		 * SID of the session.
		 */
		private String sid;
		/**
		 * Time of expiration for this session.
		 */
		private long validUntil;
		/**
		 * Map used to store permanent parameters for this session.
		 */
		private Map<String, String> map = new ConcurrentHashMap<>();
	}
}
