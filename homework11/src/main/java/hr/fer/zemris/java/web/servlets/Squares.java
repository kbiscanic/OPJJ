package hr.fer.zemris.java.web.servlets;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used to generate squares of all numbers in range from <code>a</code>
 * to <code>b</code>. <code>a</code> and <code>b</code> are received as
 * parameters from the URL. If missing, <code>a</code> is defaulted to
 * <code>0</code> and <code>b</code> to <code>20</code>. Maximal range is 20
 * numbers so <code>b</code> will be set accordingly. If <code>b>a</code> they
 * will be swapped. This servlet generates results and store them in map, then
 * forwards request to the <code>squares.jsp</code> page to display them.
 * 
 * @author Kristijan Biscanic
 * 
 */
@WebServlet("/squares")
public class Squares extends HttpServlet {

	private static final long serialVersionUID = -2288275794276162849L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Integer a = null;
		try {
			a = Integer.parseInt(req.getParameter("a"));
		} catch (NumberFormatException nfe) {
		}
		if (a == null) {
			a = 0;
		}

		Integer b = null;
		try {
			b = Integer.parseInt(req.getParameter("b"));
		} catch (NumberFormatException nfe) {
		}
		if (b == null) {
			b = 20;
		}

		if (a > b) {
			int tmp = b;
			b = a;
			a = tmp;
		}

		if (b > a + 20) {
			b = a + 20;
		}

		Map<Integer, Integer> solution = new TreeMap<>();

		for (int i = a; i <= b; i++) {
			solution.put(i, i * i);
		}

		req.setAttribute("squares", solution);

		req.getRequestDispatcher("/WEB-INF/pages/squares.jsp").forward(req,
				resp);

	}

}
