package hr.fer.zemris.java.tecaj_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Solution to problem 3 of homework03.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class NoDupLines {

	/**
	 * Main method for this program. It reads lines from console until user
	 * enters empty line and stores them in list collection. Then it calls
	 * line-reversing-duplicate-removing function.
	 * 
	 * @param args
	 *            Unused.
	 * @throws IOException
	 *             If error occurs while performing IO operations.
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in, "UTF-8"));

		List<String> list = new ArrayList<String>();

		String line = null;
		while (true) {
			line = reader.readLine();
			if (line == null) {
				break;
			}
			line = line.trim();
			if (line.isEmpty()) {
				break;
			}
			list.add(line);
		}

		String[] array = list.toArray(new String[0]);
		ispisiObrnuto(array);
	}

	/**
	 * Method used to write strings in reverse order, removing duplicates.
	 * 
	 * @param args
	 *            String array on which we are calling the method.
	 */
	private static void ispisiObrnuto(String[] args) {
		Set<String> set = new LinkedHashSet<String>();
		for (String element : args) {
			set.add(element);
		}

		List<String> list = new ArrayList<String>(set);
		Collections.reverse(list);

		for (String element : list) {
			System.out.println(element);
		}
	}

}
