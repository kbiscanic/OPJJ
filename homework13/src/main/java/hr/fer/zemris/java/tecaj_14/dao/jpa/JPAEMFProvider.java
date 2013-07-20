package hr.fer.zemris.java.tecaj_14.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Provider for JPA {@link EntityManagerFactory}.
 */
public class JPAEMFProvider {

	/**
	 * Current factory being provided on request.
	 */
	private static EntityManagerFactory emf;

	/**
	 * Provides {@link EntityManagerFactory}.
	 * 
	 * @return Factory.
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Sets {@link EntityManagerFactory} for this provider to provide.
	 * 
	 * @param emf
	 *            Factory.
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}