package hr.fer.zemris.java.tecaj_4;

import java.util.ArrayList;
import java.util.List;

public class Glavni3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Zaposlenik z1 = new Zaposlenik("1", "Pero", "Perić", "9500");
		Zaposlenik z2 = new Zaposlenik("2", "Ana", "Anić", "11000");
		Zaposlenik z3 = new Zaposlenik("3", "Jasen", "Jasić", "10000");

		List<Zaposlenik> lista = new ArrayList<>();
		lista.add(z1);
		lista.add(z2);
		lista.add(z3);

		Zaposlenik zap = unesiZaposlenika();

		System.out.println("U listi je: " + lista.contains(zap));
		System.out.println("Na poziciji je: " + lista.indexOf(zap));

	}

	private static Zaposlenik unesiZaposlenika() {
		return new Zaposlenik("3", "Jasen", "Jasić", "10000");

	}

}
