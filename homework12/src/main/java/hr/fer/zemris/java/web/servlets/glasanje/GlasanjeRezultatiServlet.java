package hr.fer.zemris.java.web.servlets.glasanje;

import hr.fer.zemris.java.web.database.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to extract results from database and prepare them to be
 * displayed by other servlets as well as <code>glasanjeRez.jsp</code> page.
 * 
 * @author Kristijan Biscanic
 * 
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Automatically generated.
	 */
	private static final long serialVersionUID = 4113464745914416247L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long pollID = null;
		try {
			pollID = Long.parseLong(req.getParameter("pollID"));
		} catch (NumberFormatException | NullPointerException ignorable) {
			req.getRequestDispatcher("/WEB-INF/pages/invalidParameters.jsp")
					.forward(req, resp);
			return;
		}

		List<VotingResultEntry> list = new ArrayList<>();

		Connection con = ConnectionFactory.getConnection();

		PreparedStatement pst = null;
		try {
			pst = con
					.prepareStatement("SELECT * FROM PollOptions WHERE pollID = ? ORDER BY votesCount DESC");
			pst.setLong(1, pollID);
			ResultSet rset = pst.executeQuery();

			while (rset.next()) {
				list.add(new VotingResultEntry(rset.getLong("id"), rset
						.getLong("votesCount"), rset.getString("optionTitle"),
						rset.getString("optionLink")));

			}
			pst.close();
			rset.close();
		} catch (SQLException sqle) {
		}

		List<VotingResultEntry> winners = new ArrayList<>();
		long wincnt = list.get(0).votes;
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
		private Long id;
		private Long votes;
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
		public VotingResultEntry(Long id, Long votes, String name,
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
		public Long getId() {
			return id;
		}

		/**
		 * Getter for the number of votes.
		 * 
		 * @return Number of votes.
		 */
		public Long getVotes() {
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
