package hr.fer.zemris.java.tecaj_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Solution to problem 5 of homework03.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class NamesCounter {

	/**
	 * Main method for this program. Program is reading names from standard
	 * inputs and stops when "quit" is entered. Before stopping, it displays
	 * every written name and number of its occurrences.
	 * 
	 * @param args
	 *            Unused.
	 * @throws IOException
	 *             If error occurs while performing IO operations.
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in, "UTF-8"));

		Map<String, Integer> map = new HashMap<String, Integer>();

		String line = null;
		while (true) {
			line = reader.readLine();
			if (line == null) {
				break;
			}
			line = line.trim();
			if (line.equalsIgnoreCase("quit")) {
				break;
			}
			if (map.containsKey(line)) {
				Integer cnt = map.get(line);
				map.remove(line);
				map.put(line, cnt + 1);
			} else {
				map.put(line, 1);
			}
		}

		for (Entry<String, Integer> name : map.entrySet()) {
			System.out.println(name.getKey() + " " + name.getValue());
		}

	}

}
