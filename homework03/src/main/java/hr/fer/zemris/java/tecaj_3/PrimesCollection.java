package hr.fer.zemris.java.tecaj_3;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Solution to problem 8 of homework03. Iterable class used to calculate prime
 * numbers without storing them.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class PrimesCollection implements Iterable<Integer> {

	private int numberOfPrimes;

	/**
	 * Default constructor.
	 * 
	 * @param numberOfPrimes
	 *            Number of prime numbers that we want to have in our
	 *            collection.
	 */
	public PrimesCollection(int numberOfPrimes) {
		super();
		this.numberOfPrimes = numberOfPrimes;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimesIterator();
	}

	/**
	 * Implementation of {@link Iterator} so we can use our
	 * {@link PrimesCollection} class in for-loops.
	 * 
	 * @author Kristijan Biscanic
	 * 
	 */
	private class PrimesIterator implements Iterator<Integer> {

		private int current;
		private int rest;

		/**
		 * Default constructor for {@link PrimesIterator} {@link Iterator}.
		 */
		public PrimesIterator() {
			super();
			this.current = 1;
			this.rest = numberOfPrimes;
		}

		@Override
		public boolean hasNext() {
			return rest > 0;
		}

		@Override
		public Integer next() {
			if (rest < 1) {
				throw new NoSuchElementException("No more elements!");
			}
			current++;
			while (!isPrime(current)) {
				current++;
			}
			rest--;
			return current;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Impossible to delete!");
		}

	}

	/**
	 * Private static method used to determine if current number is prime or
	 * not.
	 * 
	 * @param current
	 *            Number that we are checking.
	 * @return <code>true</code> if <code>current</code> is prime;
	 *         <code>false</code> otherwise.
	 */
	private static boolean isPrime(int current) {
		int limit = (int) (Math.sqrt(current) + 1);
		for (int i = 2; i < limit; i++) {
			if (current % i == 0) {
				return false;
			}
		}
		return true;
	}
}
