package hr.fer.zemris.java.tecaj_14.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web servlet used to generate response to all requests on
 * <code>servleti/RegistrationSuccessful</code> page.
 * 
 * @author Kristijan Biscanic
 * 
 */
@WebServlet("/servleti/RegistrationSuccessful")
public class RegistrationSuccess extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getRequestDispatcher("/WEB-INF/pages/RegistrationSuccessful.jsp")
				.forward(req, resp);
	}

}