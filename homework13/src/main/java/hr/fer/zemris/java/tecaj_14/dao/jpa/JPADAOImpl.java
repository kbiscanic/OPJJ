package hr.fer.zemris.java.tecaj_14.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.tecaj_14.dao.DAO;
import hr.fer.zemris.java.tecaj_14.dao.DAOException;
import hr.fer.zemris.java.tecaj_14.model.BlogEntry;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;

/**
 * DAO implementation using JPA for accessing data.
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(
				BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public BlogUser getBlogUser(Long id) throws DAOException {
		BlogUser blogUser = JPAEMProvider.getEntityManager().find(
				BlogUser.class, id);
		return blogUser;
	}

	@Override
	public BlogUser getBlogUser(String nick) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<Long> ids = (List<Long>) em
				.createQuery(
						"select user.id from BlogUser as user where user.nick=:n")
				.setParameter("n", nick).getResultList();

		if (ids.isEmpty()) {
			return null;
		}
		long id = ids.get(0);
		System.out.println(id);
		return getBlogUser(id);
	}

	@Override
	public List<BlogUser> getRegisteredUsers() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<BlogUser> users = (List<BlogUser>) em.createQuery(
				"select user from BlogUser as user").getResultList();
		return users;
	}

	@Override
	public List<BlogEntry> getBlogEntries(String nick) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();

		@SuppressWarnings("unchecked")
		List<BlogEntry> entries = (List<BlogEntry>) em
				.createQuery(
						"select entry from BlogEntry as entry where entry.creator.nick=:n")
				.setParameter("n", nick).getResultList();

		return entries;
	}

}