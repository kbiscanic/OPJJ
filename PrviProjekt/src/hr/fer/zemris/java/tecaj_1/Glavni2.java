package hr.fer.zemris.java.tecaj_1;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public class Glavni2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Slika slika = new Slika(20, 20);

		GeometrijskiLik lik1 = new Pravokutnik(2, 2, 3, 3);
		lik1.popuniLik(slika);

//		GeometrijskiLik lik2 = new Pravokutnik(8, 7, 8, 9);
//		lik2.popuniLik(slika);
		
		GeometrijskiLik lik3 = new Krug(10, 12, 2);
		lik3.popuniLik(slika);

		slika.nacrtajSliku(System.out);

	}

}
