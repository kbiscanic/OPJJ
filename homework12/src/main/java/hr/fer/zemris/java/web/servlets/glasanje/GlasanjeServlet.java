package hr.fer.zemris.java.web.servlets.glasanje;

import hr.fer.zemris.java.web.database.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web servlet used to exctract data from database and prepare them to be
 * displayed on <code>glasanjeIndex.jsp</code> page. Entries are sorted
 * ascendingly by their IDs.
 * 
 * @author Kristijan Biscanic
 * 
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * Automatically generated.
	 */
	private static final long serialVersionUID = 5175296992612815983L;

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

		req.setAttribute("id", pollID);

		List<VotingEntry> list = new ArrayList<>();

		Connection con = ConnectionFactory.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("SELECT * FROM POLLS WHERE id=?");
			pst.setLong(1, pollID);
			ResultSet rst = pst.executeQuery();
			rst.next();
			req.setAttribute("title", rst.getString("title"));
			req.setAttribute("message", rst.getString("message"));

			rst.close();
			pst.close();

			pst = con
					.prepareStatement("SELECT * FROM PollOptions WHERE pollId=?");
			pst.setLong(1, pollID);
			ResultSet rset = pst.executeQuery();

			while (rset.next()) {
				VotingEntry ve = new VotingEntry(rset.getLong("id"),
						rset.getString("optionTitle"));
				list.add(ve);
			}

			rset.close();
			pst.close();
		} catch (SQLException sqle) {

		}

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

		private Long id;
		private String name;

		/**
		 * Default constructor.
		 * 
		 * @param id
		 *            Id.
		 * @param name
		 *            Name.
		 */
		public VotingEntry(Long id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

		@Override
		public int compareTo(VotingEntry o) {
			return Long.compare(this.id, o.id);
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
		 * Getter for the name.
		 * 
		 * @return Name.
		 */
		public String getName() {
			return name;
		}

	}
}