package hr.fer.zemris.java.tecaj_4;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Glavni6 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Zaposlenik z1 = new Zaposlenik("1", "Pero", "Perić", "9500");
		Zaposlenik z2 = new Zaposlenik("2", "Ana", "Anić", "11000");
		Zaposlenik z3 = new Zaposlenik("3", "Jasen", "Jasić", "10000");

		Map<Zaposlenik, Double> bonusi = new HashMap<Zaposlenik, Double>();

		bonusi.put(z1, 1000.0);
		bonusi.put(z2, 100.0);
		bonusi.put(z3, 500.0);

		Set<Zaposlenik> set = new TreeSet<>();
		set.add(z1);
		set.add(z2);
		set.add(z3);

		Zaposlenik zap = unesiZaposlenika();

		System.out.println("U listi je: " + set.contains(zap));
		System.out.println("Jasen ima bonus: " + bonusi.get(zap));
		System.out.println("Jasen ima bonus: " + bonusi.get(z3));

	}

	private static Zaposlenik unesiZaposlenika() {
		return new Zaposlenik("3", "Jasen", "Jasić", "10000");

	}

}
