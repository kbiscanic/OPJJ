package hr.fer.zemris.java.web.servlets.glasanje;

import hr.fer.zemris.java.web.database.ConnectionFactory;

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
 * Servlet used to register a vote for one of voting entries. Id of entry
 * receiving the vote should be given as <code>id</code> parameter in the URL.
 * 
 * @author Kristijan Biscanic
 * 
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
	/**
	 * Automatically generated.
	 */
	private static final long serialVersionUID = -1715646396493231000L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Long id = null;
		try {
			id = Long.parseLong(req.getParameter("id"));
		} catch (NumberFormatException | NullPointerException ignorable) {
		}
		if (id == null) {
			req.getRequestDispatcher("/WEB-INF/pages/invalidParameters.jsp")
					.forward(req, resp);
			return;
		}

		Connection con = ConnectionFactory.getConnection();

		PreparedStatement pst = null;
		long pollID = -1;
		try {
			pst = con
					.prepareStatement("UPDATE PollOptions SET votesCount = votesCount + 1 WHERE id = ?");
			pst.setLong(1, id);
			pst.execute();

			pst.close();

			pst = con
					.prepareStatement("SELECT pollID FROM PollOptions WHERE id=?");
			pst.setLong(1, id);
			ResultSet rset = pst.executeQuery();

			rset.next();

			pollID = rset.getLong("pollID");

			rset.close();
			pst.close();

		} catch (SQLException sqle) {
		}

		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati?pollID="
				+ pollID);

	}
}
