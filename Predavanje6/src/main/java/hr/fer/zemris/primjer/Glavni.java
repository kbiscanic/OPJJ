package hr.fer.zemris.primjer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Glavni {

	public static void main(String[] args) throws IOException {

		if (args.length != 3) {
			System.err.println("Očekujem tri argumenta.");
			System.exit(0);
		}

		Map<String, Student> studentMap = ucitajStudente(args[0]);
		Map<String, Predmet> predmetMap = ucitajPredmete(args[1]);
		List<Upis> upisi = ucitajUpise(args[2], studentMap, predmetMap);

		for (Upis u : upisi) {
			System.out.println(String.format("| %-40s | %-20s | %-20s |",
					u.predmet.naziv, u.student.prezime, u.student.ime));
		}

	}

	private static String ukloniKomentare(String line) {
		int pozicija = Math.min(line.indexOf('%'), line.indexOf('#'));
		if (pozicija >= 0) {
			line = line.substring(0, pozicija);
		}
		if (line.contains("REM")) {
			line = "";
		}
		return line;

	}

	private static Map<String, Student> ucitajStudente(String datoteka)
			throws IOException {
		Map<String, Student> studentMap = new HashMap<>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					datoteka), StandardCharsets.UTF_8));
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				line = ukloniKomentare(line);
				line = line.trim();
				if (line.isEmpty())
					continue;
				String[] elems = line.split("\t");
				if (elems.length != 3) {
					System.out.println("Pogresan zapis!");
					continue;
				}
				studentMap.put(elems[0], new Student(elems[0], elems[2],
						elems[1]));
			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception ignorable) {
				}
			}
		}
		return studentMap;
	}

	private static Map<String, Predmet> ucitajPredmete(String datoteka)
			throws IOException {
		Map<String, Predmet> predmetMap = new HashMap<>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					datoteka), StandardCharsets.UTF_8));
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				line = line.trim();
				if (line.isEmpty())
					continue;
				String[] elems = line.split("\t");
				if (elems.length != 2) {
					System.out.println("Pogresan zapis!");
					continue;
				}
				predmetMap.put(elems[0], new Predmet(elems[0], elems[1]));
			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception ignorable) {
				}
			}
		}
		return predmetMap;
	}

	private static List<Upis> ucitajUpise(String datoteka,
			Map<String, Student> studenti, Map<String, Predmet> predmeti)
			throws IOException {
		List<Upis> upisi = new ArrayList<>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					datoteka), StandardCharsets.UTF_8));
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				line = line.trim();
				if (line.isEmpty())
					continue;
				String[] elems = line.split("\t");
				if (elems.length != 2) {
					System.out.println("Pogresan zapis!");
					continue;
				}
				Student s = studenti.get(elems[0]);
				Predmet p = predmeti.get(elems[1]);
				if (s == null || p == null) {
					System.out.println("Pogresan zapis!");
					continue;
				}
				upisi.add(new Upis(p, s));
			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception ignorable) {
				}
			}
		}
		return upisi;
	}

	static class Upis {
		Predmet predmet;
		Student student;

		public Upis(Predmet predmet, Student student) {
			super();
			this.predmet = predmet;
			this.student = student;
		}
	}

	static class Predmet {
		String sifra;
		String naziv;

		public Predmet(String sifra, String naziv) {
			super();
			this.sifra = sifra;
			this.naziv = naziv;
		}
	}

	static class Student {
		String jmbag;
		String ime;
		String prezime;

		public Student(String jmbag, String ime, String prezime) {
			super();
			this.jmbag = jmbag;
			this.ime = ime;
			this.prezime = prezime;
		}
	}

}
