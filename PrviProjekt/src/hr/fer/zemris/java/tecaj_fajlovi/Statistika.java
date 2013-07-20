package hr.fer.zemris.java.tecaj_fajlovi;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistika {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Očekivao sam direktorij!");
			System.exit(0);
		}

		File dir = new File(args[0]);

		SveukupniPodatci p = generirajIzvjestaj(dir);

		System.out.println("Broj datoteka = " + p.brojPojavaDatoteka);
		System.out
				.println("Ukupna vel. datoteka = " + p.ukupnaVelicinaDatoteka);
		System.out.println("Prosjecna vel. datoteka = "
				+ p.prosjecnaVelicinaDatoteka());
		System.out.println("Broj datoteka = " + p.brojDirektorija);

		List<PodatciOEkstenziji> lista = new ArrayList<>(p.mapa.values());

		Comparator<PodatciOEkstenziji> komparator = new Comparator<PodatciOEkstenziji>() {

			@Override
			public int compare(PodatciOEkstenziji o1, PodatciOEkstenziji o2) {
				return o1.ekstenzija.compareTo(o2.ekstenzija);
			}
		};

		Collections.sort(lista, Collections.reverseOrder(komparator));

		for (PodatciOEkstenziji e : lista) {
			System.out.println(e.ekstenzija + " " + e.brojPojava + " "
					+ e.sumaVelicina + " " + e.prosjek());
		}

	}

	// static class KomparatorKojiLaze implements Comparator<PodatciOEkstenziji>
	// {
	//
	// Comparator<PodatciOEkstenziji> original;
	//
	// public KomparatorKojiLaze(Comparator<PodatciOEkstenziji> original) {
	// super();
	// this.original = original;
	// }
	//
	// @Override
	// public int compare(PodatciOEkstenziji o1, PodatciOEkstenziji o2) {
	// return -original.compare(o1, o2);
	// }
	//
	// }

	static SveukupniPodatci generirajIzvjestaj(File dir) {
		SveukupniPodatci p = new SveukupniPodatci();
		rekurzivnoIspisi(dir, p);
		return p;
	}

	private static void rekurzivnoIspisi(File dir, SveukupniPodatci podatci) {
		podatci.obradiDirektorij(dir);

		File[] djeca = dir.listFiles();
		if (djeca == null) {
			return;
		}

		for (File f : djeca) {
			if (f.isDirectory()) {
				rekurzivnoIspisi(f, podatci);
			} else if (f.isFile()) {
				podatci.obradiDatoteku(f);
			}
		}

	}

	static class PodatciOEkstenziji {
		String ekstenzija;
		int brojPojava;
		long sumaVelicina;

		double prosjek() {
			return brojPojava == 0 ? 0.0 : sumaVelicina / brojPojava;
		}

		public PodatciOEkstenziji(String ekstenzija) {
			super();
			this.ekstenzija = ekstenzija;
		}

	}

	static class SveukupniPodatci {
		int brojPojavaDatoteka;
		long ukupnaVelicinaDatoteka;
		int brojDirektorija;
		Map<String, PodatciOEkstenziji> mapa = new HashMap<String, PodatciOEkstenziji>();

		double prosjecnaVelicinaDatoteka() {
			return brojPojavaDatoteka == 0 ? 0.0 : ukupnaVelicinaDatoteka
					/ brojPojavaDatoteka;
		}

		void obradiDatoteku(File f) {
			brojPojavaDatoteka++;
			ukupnaVelicinaDatoteka += f.length();

			String ekst = izvadiEkstenziju(f.getName());
			if (ekst == null) {
				return;
			}

			PodatciOEkstenziji info = mapa.get(ekst);
			if (info == null) {
				info = new PodatciOEkstenziji(ekst);
				mapa.put(ekst, info);
			}

			info.brojPojava++;
			info.sumaVelicina += f.length();
		}

		private String izvadiEkstenziju(String name) {
			int pozicijaTocke = name.lastIndexOf('.');
			if (pozicijaTocke < 1)
				return null;
			String ekst = name.substring(pozicijaTocke + 1);
			if (ekst.isEmpty())
				return null;
			return ekst.toUpperCase();
		}

		void obradiDirektorij(File d) {
			brojDirektorija++;
		}
	}

}
