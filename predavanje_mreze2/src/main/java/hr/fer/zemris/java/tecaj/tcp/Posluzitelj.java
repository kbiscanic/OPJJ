package hr.fer.zemris.java.tecaj.tcp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.imageio.ImageIO;

public class Posluzitelj {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("No port!");
			System.exit(-1);
		}

		int port = Integer.parseInt(args[0]);

		@SuppressWarnings("resource")
		ServerSocket serverSocket = new ServerSocket(port);
		while (true) {
			Socket klijent = null;
			try {
				klijent = serverSocket.accept();
			} catch (Exception ex) {
				continue;
			}
			new Thread(new Radnik(klijent)).start();
		}
	}

	static class Radnik implements Runnable {

		private Socket klijent;
		private InputStream ulaz;
		private OutputStream izlaz;

		public Radnik(Socket klijent) {
			super();
			this.klijent = klijent;
		}

		@Override
		public void run() {
			try {
				ulaz = new BufferedInputStream(klijent.getInputStream());
				izlaz = new BufferedOutputStream(klijent.getOutputStream());

				obrada();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());

			} finally {

				try {
					izlaz.flush();
				} catch (IOException ignoreable) {
				}
				try {
					klijent.close();
				} catch (IOException ignorable) {
				}
			}

		}

		private void obrada() throws IOException {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					ulaz, StandardCharsets.ISO_8859_1));

			StringBuilder zaglavlje = new StringBuilder();
			String prviRedak = reader.readLine();
			zaglavlje.append(prviRedak).append("\n");
			while (true) {
				String redak = reader.readLine();
				if (redak == null || redak.trim().isEmpty()) {
					break;
				}
				zaglavlje.append(redak).append("\n");
			}

			System.out.println("Imam klijenta: "
					+ klijent.getRemoteSocketAddress() + "\n"
					+ "Zaglavlje je: \n" + zaglavlje.toString() + "\n\n");

			String[] elementi = prviRedak.split("\\s");
			if (elementi[0].equals("GET")) {
				if (elementi[1].equals("/now")) {
					generirajNow();
					return;
				} else if (elementi[1].equals("/slika")) {
					generirajSliku();
					return;
				}
			}

			posaljiTekst(200, "text/plain; charset=UTF-8",
					"Ništa još nije implementirano");

		}

		private void generirajNow() throws IOException {
			StringBuilder sb = new StringBuilder();
			sb.append("<html>\n  <head>\n    <title> Točno vrijeme</title>\n  </head>\n  <body>\n");
			sb.append("    <p>Sada je: ").append(new Date()).append("</p>\n");
			sb.append("    <img src ='/slika'>");
			sb.append("</body>\n</html>\n");
			posaljiTekst(200, "text/html; charset=UTF-8", sb.toString());

		}

		private void posaljiTekst(int status, String mime, String text)
				throws IOException {
			posaljiBinarno(status, mime, text.getBytes(StandardCharsets.UTF_8));
		}

		private void generirajSliku() throws IOException {
			BufferedImage bim = new BufferedImage(400, 300,
					BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D g = bim.createGraphics();
			g.setColor(Color.YELLOW);
			g.fillRect(0, 0, 400, 300);

			g.setColor(Color.GREEN);
			g.fillOval(10, 10, 200, 250);

			g.setColor(Color.BLUE);
			g.fillRect(220, 150, 150, 120);

			g.setColor(Color.BLACK);
			g.drawString(new Date().toString(), 10, 290);

			g.dispose();

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(bim, "png", bos);

			byte[] okteti = bos.toByteArray();

			posaljiBinarno(200, "image/png", okteti);
		}

		private void posaljiBinarno(int status, String mime, byte[] bytes)
				throws IOException {
			StringBuilder sb = new StringBuilder();
			sb.append("HTTP/1.0 ").append(status).append(" ");
			switch (status) {
			case 200:
				sb.append("OK");
				break;
			case 404:
				sb.append("File not found");
				break;
			default:
				sb.append("x");
				break;
			}
			sb.append("\r\n");
			sb.append("Content-Type: ").append(mime).append("\r\n");
			sb.append("Content-Length: ").append(bytes.length).append("\r\n");
			sb.append("\r\n");

			izlaz.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));
			izlaz.write(bytes);
		}
	}
}
