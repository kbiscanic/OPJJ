package hr.fer.zemris.java.tecaj_1;

public class Glavni3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ParniBrojevi pb = new ParniBrojevi(12, 13);
		for (Object broj : pb) {
			System.out.println("Gledam broj: " + broj);
		}

	}

}
