package hr.fer.zemris.java.web.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to initialize data. It fills database with 2 sample polls.
 * 
 * @author Kristijan Biscanic
 * 
 */
@WebServlet("/init")
public class InitServlet extends HttpServlet {

	private static final long serialVersionUID = 3392901803983687589L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Connection con = ConnectionFactory.getConnection();

		PreparedStatement pst = null;

		ResultSet rset = null;
		try {
			pst = con
					.prepareStatement("SELECT * FROM Polls WHERE title='Glasanje za omiljeni bend'");
			rset = pst.executeQuery();

			if (!rset.next()) {
				insertPoll1(con);
			}

			rset.close();
			pst.close();

			pst = con
					.prepareStatement("SELECT * FROM Polls WHERE title='OS usage'");
			rset = pst.executeQuery();

			if (!rset.next()) {
				insertPoll2(con);
			}

			rset.close();
			pst.close();
		} catch (SQLException sqle) {
			req.getRequestDispatcher("/WEB-INF/pages/sqlError.jsp").forward(
					req, resp);
			try {
				if (rset != null) {
					rset.close();
				}
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException e) {
			}
			return;
		}

		resp.sendRedirect(req.getServletContext().getContextPath());
	}

	/**
	 * Method used to fill sample OS usage poll into database provided with
	 * <code>con</code> parameter.
	 * 
	 * @param con
	 *            Connection to the database.
	 * @throws SQLException
	 *             Exception.
	 */
	private void insertPoll2(Connection con) throws SQLException {

		PreparedStatement pst = null;
		pst = con
				.prepareStatement("INSERT INTO Polls(title, message) VALUES (?, ?)");
		pst.setString(1, "OS usage");
		pst.setString(2, "What operating system are you using?");
		pst.execute();
		pst.close();

		pst = con
				.prepareStatement("SELECT id FROM Polls WHERE title='OS usage'");
		ResultSet rset = pst.executeQuery();

		long pollID = -1;
		if (rset.next()) {
			pollID = rset.getLong("id");
		} else {
			throw new SQLException();
		}

		rset.close();
		pst.close();

		pst = con
				.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)");

		pst.setString(1, "Windows");
		pst.setString(2, "http://en.wikipedia.org/wiki/Microsoft_Windows");
		pst.setLong(3, pollID);
		pst.setLong(4, 0);
		pst.execute();

		pst.setString(1, "Linux");
		pst.setString(2, "http://en.wikipedia.org/wiki/Linux");
		pst.setLong(4, pollID);
		pst.setLong(4, 0);
		pst.execute();

		pst.setString(1, "Mac");
		pst.setString(2, "http://en.wikipedia.org/wiki/Mac_OS");
		pst.setLong(3, pollID);
		pst.setLong(4, 0);
		pst.execute();

		pst.setString(1, "Other");
		pst.setString(2, "http://en.wikipedia.org/wiki/Operating_system#Other");
		pst.setLong(3, pollID);
		pst.setLong(4, 0);
		pst.execute();

		pst.close();

	}

	/**
	 * Method used to fill sample favorite band poll into database provided with
	 * <code>con</code> parameter.
	 * 
	 * @param con
	 *            Connection to the database.
	 * @throws SQLException
	 *             Exception.
	 */
	private void insertPoll1(Connection con) throws SQLException {
		PreparedStatement pst = null;
		pst = con
				.prepareStatement("INSERT INTO Polls(title, message) VALUES (?, ?)");
		pst.setString(1, "Glasanje za omiljeni bend");
		pst.setString(2, "Od sljedećih bendova, koji Vam je bend najdraži?");
		pst.execute();
		pst.close();

		pst = con
				.prepareStatement("SELECT id FROM Polls WHERE title='Glasanje za omiljeni bend'");
		ResultSet rset = pst.executeQuery();

		long pollID = -1;
		if (rset.next()) {
			pollID = rset.getLong("id");
		} else {
			throw new SQLException();
		}

		rset.close();
		pst.close();

		pst = con
				.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)");
		pst.setString(1, "The Beatles");
		pst.setString(2,
				"http://www.geocities.com/~goldenoldies/TwistAndShout-Beatles.mid");
		pst.setLong(3, pollID);
		pst.setLong(4, 0);
		pst.execute();

		pst.setString(1, "The Platters");
		pst.setString(2,
				"http://www.geocities.com/~goldenoldies/SmokeGetsInYourEyes-Platters-ver2.mid");
		pst.setLong(3, pollID);
		pst.setLong(4, 0);
		pst.execute();

		pst.setString(1, "The Beach Boys");
		pst.setString(2,
				"http://www.geocities.com/~goldenoldies/SurfinUSA-BeachBoys.mid");
		pst.setLong(3, pollID);
		pst.setLong(4, 0);
		pst.execute();

		pst.setString(1, "The Four Seasons");
		pst.setString(2,
				"http://www.geocities.com/~goldenoldies/BigGirlsDontCry-FourSeasons.mid");
		pst.setLong(3, pollID);
		pst.setLong(4, 0);
		pst.execute();

		pst.setString(1, "The Marcels");
		pst.setString(2,
				"http://www.geocities.com/~goldenoldies/Bluemoon-Marcels.mid");
		pst.setLong(3, pollID);
		pst.setLong(4, 0);
		pst.execute();

		pst.setString(1, "The Everly Brothers");
		pst.setString(
				2,
				"http://www.geocities.com/~goldenoldies/All.I.HaveToDoIsDream-EverlyBrothers.mid");
		pst.setLong(3, pollID);
		pst.setLong(4, 0);
		pst.execute();

		pst.setString(1, "The Mamas And The Papas");
		pst.setString(2,
				"http://www.geocities.com/~goldenoldies/CaliforniaDreaming-Mamas-Papas.mid");
		pst.setLong(3, pollID);
		pst.setLong(4, 0);
		pst.execute();

		pst.close();

	}
}
