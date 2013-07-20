package hr.fer.zemris.java.tecaj_4;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class Glavni4 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Zaposlenik z1 = new Zaposlenik("1", "Pero", "Perić", "9500");
		Zaposlenik z2 = new Zaposlenik("2", "Ana", "Anić", "11000");
		Zaposlenik z3 = new Zaposlenik("3", "Jasen", "Jasić", "10000");

		Comparator<Zaposlenik> k = new Comparator<Zaposlenik>() {

			@Override
			public int compare(Zaposlenik o1, Zaposlenik o2) {
				return o1.getSifra().compareTo(o2.getSifra());
			}

		};

		Set<Zaposlenik> set = new TreeSet<>(k);
		set.add(z1);
		set.add(z2);
		set.add(z3);

		Zaposlenik zap = unesiZaposlenika();

		System.out.println("U listi je: " + set.contains(zap));

	}

	private static Zaposlenik unesiZaposlenika() {
		return new Zaposlenik("3", "Jasen", "Jasić", "10000");

	}

}
