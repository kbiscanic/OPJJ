package hr.fer.zemris.java.web;

import hr.fer.zemris.java.web.database.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to extract and prepare list of available polls and forward
 * request to <code>Index.jsp</code> for displaying.
 * 
 * @author Kristijan Biscanic
 * 
 */
@WebServlet("/index.html")
public class IndexServlet extends HttpServlet {

	/**
	 * Automatically generated.
	 */
	private static final long serialVersionUID = 3477805829710158230L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Connection con = ConnectionFactory.getConnection();

		PreparedStatement pst = null;
		ResultSet rset = null;
		Map<String, Long> polls = new HashMap<>();

		try {
			pst = con.prepareStatement("SELECT * FROM Polls");
			rset = pst.executeQuery();

			while (rset.next()) {
				polls.put(rset.getString("title"), rset.getLong("id"));
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
			} catch (SQLException s) {
			}
			return;
		}

		req.setAttribute("polls", polls);

		req.getRequestDispatcher("/WEB-INF/pages/Index.jsp").forward(req, resp);

	}

}
