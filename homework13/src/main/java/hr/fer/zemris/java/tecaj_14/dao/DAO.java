package hr.fer.zemris.java.tecaj_14.dao;

import java.util.List;

import hr.fer.zemris.java.tecaj_14.model.BlogEntry;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;

public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id
	 *            ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException
	 *             ako dođe do pogreške pri dohvatu podataka.
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Dohvaća korisnika sa zadanim <code>id</code>-em. Ako takav korisnik ne
	 * postoji, vraća <code>null</code>.
	 * 
	 * @param id
	 *            ključ zapisa
	 * @return korisnik ili <code>null</code> ako korisnik ne postoji
	 * @throws DAOException
	 *             ako dođe do pogreške pri dohvatu podataka.
	 */
	public BlogUser getBlogUser(Long id) throws DAOException;

	/**
	 * Dohvaća korisnika sa zadanim <code>nick</code>om. Ako takav korisnik ne
	 * postoji, vraća <code>null</code>.
	 * 
	 * @param nick
	 *            nick korisnika
	 * @return korisnik ili <code>null</code> ako korisnik ne postoji
	 * @throws DAOException
	 *             ako dođe do pogreške pri dohvatu podataka.
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;

	/**
	 * Dohvaća listu registriranih korisnika.
	 * 
	 * @return Lista korisnika
	 * @throws DAOException
	 *             ako dođe do pogreške pri dohvatu.
	 */
	public List<BlogUser> getRegisteredUsers() throws DAOException;

	/**
	 * Dohvaća entrye za zadanog korisnika.
	 * 
	 * @param nick
	 *            Nick korisnika.
	 * @return Lista entrya.
	 * @throws DAOException
	 *             ako dođe do pogreške pri dohvatu.
	 */
	public List<BlogEntry> getBlogEntries(String nick) throws DAOException;

}