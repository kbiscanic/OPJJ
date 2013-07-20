package hr.fer.zemris.java.web.servlets.glasanje;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web servlet used to exctract data from .txt file and prepare them to be
 * displayed on <code>glasanjeIndex.jsp</code> page. Entries are sorted
 * ascendingly by their IDs.
 * 
 * @author Kristijan Biscanic
 * 
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

	private static final long serialVersionUID = 5175296992612815983L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-definicija.txt");
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(fileName)),
						StandardCharsets.UTF_8));

		String line = null;

		List<VotingEntry> list = new ArrayList<>();
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
			VotingEntry ve = new VotingEntry(Integer.parseInt(splitted[0]
					.trim()), splitted[1].trim());
			list.add(ve);
		}

		br.close();

		Collections.sort(list);

		req.setAttribute("entryList", list);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(
				req, resp);

	}

	/**
	 * Class representing one entry containing of ID and band name.
	 * 
	 * @author Kristijan Biscanic
	 * 
	 */
	public static class VotingEntry implements Comparable<VotingEntry> {

		private Integer id;
		private String name;

		/**
		 * Default constructor.
		 * 
		 * @param id
		 *            Id.
		 * @param name
		 *            Name.
		 */
		public VotingEntry(Integer id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

		@Override
		public int compareTo(VotingEntry o) {
			return Integer.compare(this.id, o.id);
		}

		/**
		 * Getter for the ID parameter.
		 * 
		 * @return ID.
		 */
		public Integer getId() {
			return id;
		}

		/**
		 * Getter for the name.
		 * 
		 * @return Name.
		 */
		public String getName() {
			return name;
		}

	}
}