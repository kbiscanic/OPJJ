package hr.fer.zemris.java.tecaj_14.web.servlets;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogComment;
import hr.fer.zemris.java.tecaj_14.model.BlogEntry;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Web servlet used to generate responses to all requests with URL like:
 * <code>/servleti/author/*</code>.
 * 
 * @author Kristijan Biscanic
 * 
 */
@WebServlet("/servleti/author/*")
public class ListEntries extends HttpServlet {

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
		String info = req.getPathInfo();
		if (info == null) {
			resp.sendError(404);
			return;
		}

		info = info.substring(1);
		String[] infos = info.split("/");
		String nick = infos[0];
		req.setAttribute("nick", nick);

		if (infos.length == 1) {

			List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntries(nick);

			req.setAttribute("entries", entries);

			req.getRequestDispatcher("/WEB-INF/pages/ListEntries.jsp").forward(
					req, resp);
			return;
		} else if (infos.length != 2) {
			resp.sendError(404);
			return;
		} else {
			if (infos[1].equals("save")) {
				String title = req.getParameter("title");
				String text = req.getParameter("text");

				if (title == null || text == null) {
					resp.sendRedirect("../" + nick);
					return;
				}

				title = title.trim();
				text = text.trim();

				EntityManagerFactory emf = (EntityManagerFactory) req
						.getServletContext().getAttribute("my.application.emf");

				EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();

				BlogEntry be = null;

				try {
					be = DAOProvider.getDAO().getBlogEntry(
							Long.parseLong(req.getParameter("id")));
				} catch (NumberFormatException nfe) { // doesn't matter
				}

				if (be == null) {
					be = new BlogEntry();
					be.setCreatedAt(new Date());
					be.setCreator(DAOProvider.getDAO().getBlogUser(nick));

				}

				if (title.isEmpty()) {
					req.setAttribute("error",
							"Title must contain at least 1 character!");
					req.setAttribute("entry", be);
					req.getRequestDispatcher("/WEB-INF/pages/Entry.jsp")
							.forward(req, resp);
					em.getTransaction().rollback();
					em.close();
					return;
				}
				be.setTitle(title);
				if (text.isEmpty()) {
					req.setAttribute("error",
							"Text must contain at least 1 character!");
					req.setAttribute("entry", be);
					req.getRequestDispatcher("/WEB-INF/pages/Entry.jsp")
							.forward(req, resp);
					em.getTransaction().rollback();
					em.close();
					return;
				}
				be.setText(text);

				be.setLastModifiedAt(new Date());

				em.merge(be);
				em.getTransaction().commit();
				em.close();

				resp.sendRedirect("../" + nick);
				return;
			} else if (infos[1].equals("new") || infos[1].equals("edit")) {
				if (!req.getSession().getAttribute("nick").equals(nick)) {
					resp.sendError(401);
					return;
				}

				BlogEntry entry = null;
				if (infos[1].equals("new")) {
					entry = new BlogEntry();
				} else {
					String sId = req.getParameter("id");
					Long id = null;
					try {
						id = Long.parseLong(sId);
					} catch (NumberFormatException nfe) {
						resp.sendError(400);
						return;
					}
					entry = DAOProvider.getDAO().getBlogEntry(id);
				}
				req.setAttribute("entry", entry);

				req.getRequestDispatcher("/WEB-INF/pages/Entry.jsp").forward(
						req, resp);
				return;
			} else {
				Long id;
				try {
					id = Long.parseLong(infos[1]);
				} catch (NumberFormatException nfe) {
					resp.sendError(400);
					return;
				}
				BlogEntry be = DAOProvider.getDAO().getBlogEntry(id);
				if (be == null || !be.getCreator().getNick().equals(nick)) {
					resp.sendError(400);
					return;
				}

				String commEmail = req.getParameter("email");
				String commMessage = req.getParameter("message");

				if (commEmail != null && commMessage != null) {
					commEmail = commEmail.trim();
					commMessage = commMessage.trim();

					if (commEmail.isEmpty() || commMessage.isEmpty()) {
						req.setAttribute("error", "Both fields must be filled!");
					}

					else if (!commEmail
							.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
						req.setAttribute("error", "Invalid e-mail address!");
						commEmail = "";
					}

					if (!commEmail.isEmpty() && !commMessage.isEmpty()) {
						BlogComment bc = new BlogComment();
						bc.setMessage(commMessage);
						bc.setPostedOn(new Date());
						bc.setUsersEMail(commEmail);
						bc.setBlogEntry(be);

						EntityManagerFactory emf = (EntityManagerFactory) req
								.getServletContext().getAttribute(
										"my.application.emf");

						EntityManager em = emf.createEntityManager();
						em.getTransaction().begin();
						em.persist(bc);
						em.getTransaction().commit();
						em.close();
						resp.sendRedirect("" + id);
						return;
					}

					req.setAttribute("email", commEmail);
					req.setAttribute("message", commMessage);
				}

				req.setAttribute("entry", be);
				req.setAttribute("nick", nick);
				req.getRequestDispatcher("/WEB-INF/pages/DisplayEntry.jsp")
						.forward(req, resp);

			}

		}

	}
}
