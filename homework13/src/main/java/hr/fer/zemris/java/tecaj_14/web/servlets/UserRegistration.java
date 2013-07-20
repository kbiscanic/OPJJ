package hr.fer.zemris.java.tecaj_14.web.servlets;

import hr.fer.zemris.java.tecaj_14.model.BlogUser;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web servlet used to generate response to all requests on
 * <code>servleti/register</code> page.
 * 
 * @author Kristijan Biscanic
 * 
 */
@WebServlet("/servleti/register")
public class UserRegistration extends HttpServlet {

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
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		String nick = req.getParameter("nick");
		String password = req.getParameter("password");

		if (firstName == null || lastName == null || email == null
				|| nick == null || password == null) {
			back(firstName, lastName, email, nick, req, resp);
			return;
		}
		firstName = firstName.trim();
		lastName = lastName.trim();
		email = email.trim();
		nick = nick.trim();
		password = password.trim();

		System.out.println(lastName);

		if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()
				|| nick.isEmpty() || password.isEmpty()) {
			req.setAttribute("error", "All fields must be filled!");
			back(firstName, lastName, email, nick, req, resp);
			return;
		}

		if (!email
				.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			req.setAttribute("error", "Invalid e-mail address!");
			back(firstName, lastName, "", nick, req, resp);
			return;
		}

		EntityManagerFactory emf = (EntityManagerFactory) req
				.getServletContext().getAttribute("my.application.emf");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		BlogUser newUser = new BlogUser();
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setEmail(email);
		newUser.setNick(nick);
		newUser.setPassword(password);

		try {
			em.persist(newUser);
			em.getTransaction().commit();
			em.close();
		} catch (PersistenceException exepction) {
			req.setAttribute("error",
					"Nickname already exists! Please choose another one!");
			back(firstName, lastName, email, "", req, resp);
			return;
		}

		resp.sendRedirect("RegistrationSuccessful");
	}

	private void back(String firstName, String lastName, String email,
			String nick, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("firstName", firstName);
		req.setAttribute("lastName", lastName);
		req.setAttribute("email", email);
		req.setAttribute("nick", nick);
		req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req,
				resp);
		return;
	}

}