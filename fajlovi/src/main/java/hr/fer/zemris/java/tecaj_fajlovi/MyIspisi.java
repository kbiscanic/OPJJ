package hr.fer.zemris.java.tecaj_fajlovi;

import java.io.File;

public class MyIspisi {

	public static void main(String[] args) {
		File root = new File("/");

		ispisiDjecu(root);
	}

	private static void ispisiDjecu(File starting) {
		System.out.println(starting);
		File[] djeca = starting.listFiles();
		if (djeca == null)
			return;
		for (File file : djeca) {
			ispisiDjecu(file);
		}
	}
}
