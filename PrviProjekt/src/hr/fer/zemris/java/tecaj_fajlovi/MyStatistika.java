package hr.fer.zemris.java.tecaj_fajlovi;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MyStatistika {

	private static Kolekcija kol;

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Očekivao sam direktorij!");
			System.exit(0);
		}

		kol = new Kolekcija();

		rekurzijica(new File(args[0]));

	}

	private static void rekurzijica(File dir) {
		File[] djeca = dir.listFiles();
		if (djeca == null) {
			return;
		}

		for (File f : djeca) {
			if (f.isDirectory()) {
				rekurzijica(f);
			} else if (f.isFile()) {
				String[] temp = f.getName().split(".");
				kol.insert(temp[temp.length - 1], f.getTotalSpace());
			}
		}

	}

	static class Kolekcija {

		private Map<String, Integer> ekstenzija;
		private Map<Integer, Integer> broj;
		private Map<Integer, Long> velicina;
		private int curSize = 0;

		public Kolekcija() {
			super();
			ekstenzija = new HashMap<String, Integer>();
			velicina = new HashMap<Integer, Long>();
			broj = new HashMap<Integer, Integer>();
		}

		public void insert(String ext, long size) {
			if (ekstenzija.containsKey(ext)) {
				int index = ekstenzija.get(ext);
				Integer num = broj.get(index);
				num++;
				broj.remove(index);
				broj.put(index, num);
				Long siz = velicina.get(index);
				siz += size;
				velicina.remove(index);
				velicina.put(index, siz);
			} else {
				int index = curSize;
				curSize++;
				ekstenzija.put(ext, index);
				broj.put(index, 1);
				velicina.put(index, size);

			}
		}

	}
}
