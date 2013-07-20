package hr.fer.zemris.java.tecaj_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Glavni2 {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));

		Map<String, Integer> brojPojav = new HashMap<String, Integer>();

		while (true) {
			String redak = reader.readLine();
			if (redak == null)
				break;
			redak = redak.trim();
			if (redak.isEmpty())
				continue;
			if (redak.equals("quit"))
				break;

			String ime = redak;

			Integer broj = brojPojav.get(ime);

			if (broj == null) {
				brojPojav.put(ime, 1);
				continue;
			}

			brojPojav.put(ime, broj + 1);
		}

		for (Map.Entry<String, Integer> e : brojPojav.entrySet()) {
			System.out.println(e.getKey() + " ==> " + e.getValue());
		}
	}
}
