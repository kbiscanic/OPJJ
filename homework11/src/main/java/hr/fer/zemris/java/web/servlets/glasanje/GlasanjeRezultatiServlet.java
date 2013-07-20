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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to extract results from <code>.txt</code> file and prepare them
 * to be displayed by other servlets as well as <code>glasanjeRez.jsp</code>
 * page.
 * 
 * @author Kristijan Biscanic
 * 
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	private static final long serialVersionUID = 4113464745914416247L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String resultsFileName = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-rezultati.txt");
		File results = new File(resultsFileName);

		String fileName = req.getServletContext().getRealPath(
				"/WEB-INF/glasanje-definicija.txt");
		BufferedReader defReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(fileName)),
						StandardCharsets.UTF_8));

		if (!results.exists()) {
			defReader.mark(0);
			if (results.createNewFile() == false) {
				defReader.close();
				return;
			}

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(results), StandardCharsets.UTF_8));

			String line = null;

			while (true) {
				line = defReader.readLine();
				if (line == null) {
					break;
				}
				line = line.trim();
				if (line.isEmpty()) {
					break;
				}
				String[] splitted = line.split("\t");
				bw.write(splitted[0].trim() + "\t" + 0);
				bw.newLine();
			}

			defReader.reset();
			bw.flush();
			bw.close();
		}

		Map<Integer, Integer> votes = new TreeMap<>();
		String line = null;
		BufferedReader resultReader = new BufferedReader(new InputStreamReader(
				new FileInputStream(results), StandardCharsets.UTF_8));
		while (true) {
			line = resultReader.readLine();
			if (line == null) {
				break;
			}
			line = line.trim();
			if (line.isEmpty()) {
				break;
			}
			String[] splitted = line.split("\t");

			votes.put(Integer.parseInt(splitted[0].trim()),
					Integer.parseInt(splitted[1].trim()));
		}
		resultReader.close();

		List<VotingResultEntry> list = new ArrayList<>();

		line = null;
		while (true) {
			line = defReader.readLine();
			if (line == null) {
				break;
			}
			line = line.trim();
			if (line.isEmpty()) {
				break;
			}
			String[] splitted = line.split("\t");
			int id = Integer.parseInt(splitted[0].trim());
			int noVotes = votes.get(id);
			list.add(new VotingResultEntry(id, noVotes, splitted[1].trim(),
					splitted[2].trim()));
		}

		defReader.close();

		Collections.sort(list);

		List<VotingResultEntry> winners = new ArrayList<>();
		int wincnt = list.get(0).votes;
		for (VotingResultEntry vre : list) {
			if (vre.votes != wincnt) {
				break;
			}
			winners.add(vre);
		}

		req.getSession().setAttribute("results", list);
		req.setAttribute("winners", winners);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req,
				resp);

	}

	/**
	 * Class representing one voting result entry, determined by voting ID,
	 * number of votes, band name and hyperlink to one their song.
	 * 
	 * @author Kristijan Biscanic
	 * 
	 */
	public static class VotingResultEntry implements
			Comparable<VotingResultEntry> {
		private Integer id;
		private Integer votes;
		private String name;
		private String hyperlink;

		/**
		 * Default constructor.
		 * 
		 * @param id
		 *            Id.
		 * @param votes
		 *            Votes.
		 * @param name
		 *            Name.
		 * @param hyperlink
		 *            Hyperlink.
		 */
		public VotingResultEntry(Integer id, Integer votes, String name,
				String hyperlink) {
			super();
			this.id = id;
			this.votes = votes;
			this.name = name;
			this.hyperlink = hyperlink;
		}

		@Override
		public int compareTo(VotingResultEntry o) {
			return o.votes.compareTo(this.votes);
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
		 * Getter for the number of votes.
		 * 
		 * @return Number of votes.
		 */
		public Integer getVotes() {
			return votes;
		}

		/**
		 * Getter for the name.
		 * 
		 * @return Name.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Getter for the hyperlink parameter.
		 * 
		 * @return Hyperlink.
		 */
		public String getHyperlink() {
			return hyperlink;
		}

	}
}
