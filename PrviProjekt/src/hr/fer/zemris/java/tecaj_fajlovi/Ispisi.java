package hr.fer.zemris.java.tecaj_fajlovi;

import java.io.File;

public class Ispisi {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Očekivao sam direktorij!");
			System.exit(0);
		}

		File dir = new File(args[0]);

		rekurzivnoIspisi(dir);

	}

	private static void rekurzivnoIspisi(File dir) {
		System.out.println(dir);
		File[] djeca = dir.listFiles();
		if (djeca == null) {
			return;
		}

		for (File f : djeca) {
			if (f.isDirectory()) {
				rekurzivnoIspisi(f);
			} else if (f.isFile()) {
				System.out.println(f);
			}
		}

	}

}
