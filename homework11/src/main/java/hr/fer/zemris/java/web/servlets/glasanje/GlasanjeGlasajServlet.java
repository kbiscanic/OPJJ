package hr.fer.zemris.java.web.servlets.glasanje;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to register a vote for one of voting entries. Id of entry
 * receiving the vote should be given as <code>id</code> parameter in the URL.
 * 
 * @author Kristijan Biscanic
 * 
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	private static final long serialVersionUID = -1715646396493231000L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String resultsFileName = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-rezultati.txt");
		File results = new File(resultsFileName);

		Integer id = null;
		try {
			id = Integer.parseInt(req.getParameter("id"));
		} catch (NumberFormatException | NullPointerException ignorable) {
		}
		if (id == null) {
			req.getRequestDispatcher("/WEB-INF/pages/invalidParameters.jsp")
					.forward(req, resp);
			return;
		}

		if (!results.exists()) {
			if (results.createNewFile() == false) {
				return;
			}

			String fileName = req.getServletContext().getRealPath(
					"/WEB-INF/glasanje-definicija.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(fileName)),
					StandardCharsets.UTF_8));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(results), StandardCharsets.UTF_8));

			String line = null;

			while (true) {
				line = br.readLine();
				if (line == null) {
					break;
				}
				line = line.trim();
				if (line.isEmpty()) {
					break;
				}
				String[] splitted = line.split("\t");
				int val = 0;
				if (Integer.parseInt(splitted[0].trim()) == id) {
					val = 1;
				}
				bw.write(splitted[0].trim() + "\t" + val);
				bw.newLine();
			}

			br.close();
			bw.flush();
			bw.close();
		} else {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(results), StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();

			String line = null;
			boolean written = false;
			while (true) {
				line = br.readLine();
				if (line == null) {
					break;
				}
				line = line.trim();
				if (line.isEmpty()) {
					break;
				}
				String[] splitted = line.split("\t");
				if (Integer.parseInt(splitted[0].trim()) == id) {
					splitted[1] = Integer.toString(Integer.parseInt(splitted[1]
							.trim()) + 1);
					sb.append(splitted[0] + "\t" + splitted[1]);
					written = true;
				} else {
					sb.append(line);
				}
				sb.append("\n");
			}
			if (!written) {
				sb.append(id + "\t" + 1 + "\n");
			}
			br.close();

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(results), StandardCharsets.UTF_8));
			bw.write(sb.toString());
			bw.flush();
			bw.close();
		}

		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");

	}
}
