package hr.fer.zemris.primjer.strategija;

/**
 * Strategy design pattern interface. The classes that implement a concrete
 * strategy should implement this. The context class uses this to call the
 * concrete strategy.
 */
public interface IObrada<T> {

	/**
	 * Number of columns in file.
	 * 
	 * @return Number of columns.
	 */
	int brojStupaca();

	/**
	 * Method telling what should be done with every line in file.
	 * 
	 * @param elems
	 *            Elements of that line.
	 */
	void obradiRedak(String[] elems);

	/**
	 * Method telling what should be returned as the solution.
	 * 
	 * @return Solution.
	 */
	T dohvatiRezultat();

}
