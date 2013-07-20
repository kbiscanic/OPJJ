package hr.fer.zemris.java.tecaj_1;

import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public class GeometrijskiLik {
	private String ime;

	public GeometrijskiLik(String ime) {
		this.ime = ime;
	}

	public String getIme() {
		return ime;
	}

	public double getOpseg() {
		return 0;
	}

	public double getPovrsina() {
		return 0;
	}

	public boolean sadrziTocku(int x, int y) {
		return false;
	}

	public void popuniLik(Slika slika) {
		int visina = slika.getVisina();
		int sirina = slika.getSirina();

		for (int y = 0; y < visina; y++) {
			for (int x = 0; x < sirina; x++) {
				if (sadrziTocku(x, y)) {
					slika.upaliTocku(x, y);
				}
			}
		}
	}

}
