package hr.fer.zemris.java.tecaj_14.dao;

import hr.fer.zemris.java.tecaj_14.dao.jpa.JPADAOImpl;

/**
 * Provider for DAO implementation.
 */
public class DAOProvider {

	private static DAO dao = new JPADAOImpl();

	/**
	 * Provides concrete DAO implementation.
	 * 
	 * @return DAO
	 */
	public static DAO getDAO() {
		return dao;
	}

}