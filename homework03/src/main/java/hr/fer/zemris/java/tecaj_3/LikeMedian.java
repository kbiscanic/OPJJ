package hr.fer.zemris.java.tecaj_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Solution to problem 7 of homework03.
 * 
 * @author Kristijan Biscanic
 * 
 * @param <T>
 *            Should be comparable.
 */
public class LikeMedian<T> {

	private List<T> list;
	private int cnt;

	/**
	 * Default constructor. Creating an empty {@link ArrayList} for storing
	 * elements.
	 */
	public LikeMedian() {
		super();
		list = new ArrayList<T>();
		cnt = 0;
	}

	/**
	 * Method used to add an object to current list.
	 * 
	 * @param object
	 *            Object that needs to be added.
	 */
	public void add(T object) {
		cnt++;
		list.add(object);
	}

	/**
	 * Method used to determine a median for all objects currently in the list.
	 * 
	 * @return Median.
	 */
	public T get() {
		Collections.sort(list, new Comparator<T>() {

			@SuppressWarnings("unchecked")
			@Override
			public int compare(T o1, T o2) {
				return ((Comparable<Comparable<?>>) o1)
						.compareTo((Comparable<?>) o2);
			}

		});
		cnt--;
		return list.get((int) ((double) cnt / 2));
	}
}
