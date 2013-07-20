package hr.fer.zemris.java.tecaj_3;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Solution to problem 1 of homework03.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class PrimjerSkupa {

	/**
	 * Main method for this program. Displays all arguments removing duplicates.
	 * 
	 * @param args
	 *            Arguments for this program.
	 */
	public static void main(String[] args) {
		System.out.println("Preko HashSet-a:");
		ispisiSkup(ukloniDuplikate1(args));
		System.out.println();

		System.out.println("Preko TreeHashSet-a:");
		ispisiSkup(ukloniDuplikate2(args));
		System.out.println();

		System.out.println("Preko LinkedHashSet-a:");
		ispisiSkup(ukloniDuplikate3(args));
		System.out.println();
	}

	/**
	 * Private static method used to display given collection.
	 * 
	 * @param col
	 *            Collection that needs to be displayed.
	 */
	private static void ispisiSkup(Collection<String> col) {
		for (String element : col) {
			System.out.println(element);
		}
	}

	/**
	 * Private static method used to display given collection.
	 * 
	 * @param col
	 *            Collection that needs to be displayed.
	 */
	@SuppressWarnings("unused")
	private static void ispisiSkup2(Collection<String> col) {
		Iterator<String> iterator = col.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}

	/**
	 * Private static method used to remove duplicates from given String[]. This
	 * method uses {@link HashSet}.
	 * 
	 * @param polje
	 *            String[].
	 * @return Collection with removed duplicates.
	 */
	private static Collection<String> ukloniDuplikate1(String[] polje) {
		Set<String> set = new HashSet<String>();
		for (String element : polje) {
			set.add(element);
		}
		return set;
	}

	/**
	 * Private static method used to remove duplicates from given String[]. This
	 * method uses {@link TreeSet}.
	 * 
	 * @param polje
	 *            String[].
	 * @return Collection with removed duplicates.
	 */
	private static Collection<String> ukloniDuplikate2(String[] polje) {
		Set<String> set = new TreeSet<String>();
		for (String element : polje) {
			set.add(element);
		}
		return set;
	}

	/**
	 * Private static method used to remove duplicates from given String[]. This
	 * method uses {@link LinkedHashSet}.
	 * 
	 * @param polje
	 *            String[].
	 * @return Collection with removed duplicates.
	 */
	private static Collection<String> ukloniDuplikate3(String[] polje) {
		Set<String> set = new LinkedHashSet<String>();
		for (String element : polje) {
			set.add(element);
		}
		return set;
	}
}
