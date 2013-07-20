package hr.fer.zemris.java.tecaj_4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Glavni {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] elementi = { "\n", "Zagreb", "Osijek", "Split", "Zagreb",
				"Karlovac", "Split" };

		napravi(elementi);
		napravi2(elementi);
		napravi3(elementi);
	}

	private static void napravi(String[] elementi) {
		List<String> lista = new ArrayList<>();

		for (String element : elementi) {
			if (!lista.contains(element)) {
				lista.add(element);
			}
		}

		Collections.reverse(lista);

		for (String grad : lista) {
			System.out.println(grad);
		}
	}

	private static void napravi2(String[] elementi) {
		List<String> lista = new ArrayList<>();
		Set<String> unikati = new HashSet<>();

		for (String element : elementi) {
			if (unikati.add(element)) {
				lista.add(element);
			}
		}

		Collections.reverse(lista);

		for (String grad : lista) {
			System.out.println(grad);
		}
	}

	private static void napravi3(String[] elementi) {
		Set<String> unikati = new LinkedHashSet<>();

		for (String element : elementi) {
			unikati.add(element);
		}

		List<String> lista = new ArrayList<>(unikati);
		Collections.reverse(lista);

		for (String grad : lista) {
			System.out.println(grad);
		}
	}

}
