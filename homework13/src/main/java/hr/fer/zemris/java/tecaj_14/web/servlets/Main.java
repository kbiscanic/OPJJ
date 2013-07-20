package hr.fer.zemris.java.tecaj_14.web.servlets;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web servlet used to generate response to all requests on
 * <code>servleti/main</code> page.
 * 
 * @author Kristijan Biscanic
 * 
 */
@WebServlet("/servleti/main")
public class Main extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String nick = req.getParameter("nick");
		String password = req.getParameter("password");

		if (nick != null && password != null) {
			nick = nick.trim();
			password = password.trim();

			if (nick.isEmpty() || password.isEmpty()) {
				req.setAttribute("error", "Both fields must be filled!");
				req.setAttribute("nick", nick);
			} else {
				BlogUser candidate = DAOProvider.getDAO().getBlogUser(nick);
				if (candidate != null && candidate.checkPassword(password)) {
					req.getSession().setAttribute("id", candidate.getId());
					req.getSession().setAttribute("fn",
							candidate.getFirstName());
					req.getSession()
							.setAttribute("ln", candidate.getLastName());
					req.getSession().setAttribute("nick", candidate.getNick());
				} else {
					req.setAttribute("error", "Invalid username or password!");
					req.setAttribute("nick", nick);
				}
			}
		}

		List<BlogUser> users = DAOProvider.getDAO().getRegisteredUsers();

		req.setAttribute("users", users);

		req.getRequestDispatcher("/WEB-INF/pages/Index.jsp").forward(req, resp);

	}

}