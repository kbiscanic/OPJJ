package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Request context for requests sent by clients to our server. This class is
 * used to generating headers and responses to that requests.
 * 
 * @author Kristijan Biscanic
 */
public class RequestContext {

	/**
	 * Output stream to which are we writing data.
	 */
	private OutputStream outputStream;
	/**
	 * Charset in which are data will be written.
	 */
	private Charset charset;

	/**
	 * Encoding of our response. Defaults to "UTF-8".
	 */
	private String encoding = "UTF-8";
	/**
	 * Status code for our response. Defaults to 200.
	 */
	private int statusCode = 200;
	/**
	 * Status text for our response. Defaults to "OK".
	 */
	private String statusText = "OK";
	/**
	 * Mime type for our content. Defaults to "text/html".
	 */
	private String mimeType = "text/html";

	/**
	 * Parameters for this {@link RequestContext}.
	 */
	private Map<String, String> parameters;
	/**
	 * Temporary arameters for this {@link RequestContext}.
	 */
	private Map<String, String> temporaryParameters = new HashMap<>();
	/**
	 * Permanent arameters for this {@link RequestContext}.
	 */
	private Map<String, String> persistentParameters;
	/**
	 * List of cookies.
	 */
	private List<RCCookie> outputCookies;
	/**
	 * Boolean telling if we had already sent header to the client.
	 */
	private boolean headerGenerated = false;

	/**
	 * Constructor for this class.
	 * 
	 * @param outputStream
	 *            Output stream.
	 * @param parameters
	 *            Parameters.
	 * @param persistent
	 *            Parameters Persistent parameters.
	 * @param outputCookies
	 *            Cookies.
	 */
	public RequestContext(OutputStream outputStream,
			Map<String, String> parameters,
			Map<String, String> persistentParameters,
			List<RCCookie> outputCookies) {
		super();
		if (outputStream == null) {
			throw new NullPointerException("Output stream must not be null!");
		}
		this.outputStream = outputStream;
		this.parameters = parameters;
		this.persistentParameters = persistentParameters;
		this.outputCookies = outputCookies;
	}

	/**
	 * Method used to get parameter by name.
	 * 
	 * @param name
	 *            Name of parameter.
	 * @return Value of parameter.
	 */
	public String getParameter(String name) {
		if (parameters == null) {
			return null;
		}
		return parameters.get(name);
	}

	/**
	 * Method used to get names of all stored parameters.
	 * 
	 * @return Set of parameter names.
	 */
	public Set<String> getParameterNames() {
		if (parameters == null) {
			return null;
		}
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Method used to get persistent parameter by name.
	 * 
	 * @param name
	 *            Name of parameter.
	 * @return Value of parameter.
	 */
	public String getPersistentParameter(String name) {
		if (persistentParameters == null) {
			return null;
		}
		return persistentParameters.get(name);
	}

	/**
	 * Method used to get names of all persistent parameters.
	 * 
	 * @return Set of parameter names.
	 */
	public Set<String> getPersistentParameterNames() {
		if (persistentParameters == null) {
			return null;
		}
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Method used to set persistent parameter <code>name</code> to given
	 * <code>value</code>.
	 * 
	 * @param name
	 *            Name of parameter.
	 * @param value
	 *            New value for this parameter.
	 */
	public void setPersistentParameter(String name, String value) {
		if (persistentParameters == null) {
			return;
		}
		persistentParameters.put(name, value);
	}

	/**
	 * Method used to remove persistent parameter by name.
	 * 
	 * @param name
	 *            Name of parameter.
	 */
	public void removePersistentParameter(String name) {
		if (persistentParameters == null) {
			return;
		}
		persistentParameters.remove(name);
	}

	/**
	 * Method used to get temporary parameter by name.
	 * 
	 * @param name
	 *            Name of parameter.
	 * @return Value of parameter.
	 */
	public String getTemporaryParameter(String name) {
		if (temporaryParameters == null) {
			return null;
		}
		return temporaryParameters.get(name);
	}

	/**
	 * Method used to get names of all temporary parameters.
	 * 
	 * @return Set of parameter names.
	 */
	public Set<String> getTemporaryParameterNames() {
		if (temporaryParameters == null) {
			return null;
		}
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Method used to set temporary parameter <code>name</code> to given
	 * <code>value</code>.
	 * 
	 * @param name
	 *            Name of parameter.
	 * @param value
	 *            New value for this parameter.
	 */
	public void setTemporaryParameter(String name, String value) {
		if (temporaryParameters == null) {
			return;
		}
		temporaryParameters.put(name, value);
	}

	/**
	 * Method used to remove temporary parameter by name.
	 * 
	 * @param name
	 *            Name of parameter.
	 */
	public void removeTemporaryParameter(String name) {
		if (temporaryParameters == null) {
			return;
		}
		temporaryParameters.remove(name);
	}

	/**
	 * Method used to set encoding of our response.
	 * 
	 * @param encoding
	 *            New encoding.
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.encoding = encoding;
	}

	/**
	 * Method used to set status code of our response.
	 * 
	 * @param statusCode
	 *            New status code.
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.statusCode = statusCode;
	}

	/**
	 * Method used to set status text of our response.
	 * 
	 * @param statusText
	 *            New status text.
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.statusText = statusText;
	}

	/**
	 * Method used to set mime type of our response.
	 * 
	 * @param mimeType
	 *            New mime type.
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) {
			throw new RuntimeException();
		}
		this.mimeType = mimeType;
	}

	/**
	 * Method used to write given array of bytes.
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	public RequestContext write(byte[] data) throws IOException {

		if (!headerGenerated) {
			generateHeader();
		}

		outputStream.write(data);

		return this;
	}

	/**
	 * Method used to write given string.
	 * 
	 * @param text
	 * @return
	 * @throws IOException
	 */
	public RequestContext write(String text) throws IOException {

		if (!headerGenerated) {
			generateHeader();
		}

		return write(text.getBytes(charset));
	}

	/**
	 * Method used to generate and write header.
	 * 
	 * @throws IOException
	 */
	protected void generateHeader() throws IOException {
		charset = Charset.forName(encoding);

		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 ").append(statusCode).append(" ")
				.append(statusText).append("\r\n");
		sb.append("Content-Type: ").append(mimeType);
		if (mimeType.matches("text/.*")) {
			sb.append("; charset=").append(encoding);
		}
		sb.append("\r\n");
		if (outputCookies != null && !outputCookies.isEmpty()) {
			for (RCCookie c : outputCookies) {
				sb.append("Set-Cookie: ").append(c.getName()).append("=\"")
						.append(c.getValue()).append("\"");
				if (c.getDomain() != null) {
					sb.append("; Domain=").append(c.getDomain());
				}
				if (c.getPath() != null) {
					sb.append("; Path=").append(c.getPath());
				}
				if (c.getMaxAge() != null) {
					sb.append("; maxAge=").append(c.getMaxAge());
				}
				sb.append("\r\n");
			}
		}
		sb.append("\r\n");

		outputStream.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));
		headerGenerated = true;

	}

	/**
	 * Inner class representing a cookie.
	 * 
	 * @author Kristijan Biscanic
	 * 
	 */
	public static class RCCookie {
		/**
		 * Name of cookie.
		 */
		private String name;
		/**
		 * Value of cookie.
		 */
		private String value;
		/**
		 * Domain of cookie.
		 */
		private String domain;
		/**
		 * Path of cookie.
		 */
		private String path;
		/**
		 * Max age of cookie.
		 */
		private Integer maxAge;

		/**
		 * Default constructor for cookie.
		 * 
		 * @param name
		 *            Name.
		 * @param value
		 *            Value.
		 * @param maxAge
		 *            Max age.
		 * @param domain
		 *            Domain.
		 * @param path
		 *            Path.
		 */
		public RCCookie(String name, String value, Integer maxAge,
				String domain, String path) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public String getDomain() {
			return domain;
		}

		public String getPath() {
			return path;
		}

		public Integer getMaxAge() {
			return maxAge;
		}

	}

	/**
	 * Method used to add cookie to the response.
	 * 
	 * @param rcCookie
	 */
	public void addRCCookie(RCCookie rcCookie) {
		if (outputCookies == null) {
			return;
		}
		outputCookies.add(rcCookie);
	}

}
