package hr.fer.zemris.java.tecaj_fajlovi;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
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
		File dir = new File("/home/x/Java/datoteke-vjezba1");

		Map<String, Student> studenti = ucitajStudente(new File(dir,
				"studenti.txt"));
		Map<String, Predmet> predmeti = ucitajPredmete(new File(dir,
				"predmeti.txt"));
		List<Upis> upisi = ucitajUpise(new File(dir, "upisi.txt"), studenti,
				predmeti);

		for (Upis u : upisi) {
			System.out.println(String.format("| %-40s | %-20s | %-20s |",
					u.predmet.naziv, u.student.prezime, u.student.ime));
		}
	}

	private static List<Upis> ucitajUpise(File file,
			Map<String, Student> studenti, Map<String, Predmet> predmeti)
			throws IOException {
		List<Upis> upisi = new ArrayList<>();
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(
					new BufferedInputStream(new FileInputStream(file)),
					StandardCharsets.UTF_8));
			while (true) {
				String redak = br.readLine();
				if (redak == null)
					break;
				redak = redak.trim();
				if (redak.isEmpty())
					continue;
				String[] elems = redak.split("\t");
				if (elems.length != 2)
					continue;
				Student student = studenti.get(elems[0]);
				Predmet predmet = predmeti.get(elems[1]);

				if (student == null || predmet == null)
					continue;

				upisi.add(new Upis(student, predmet));
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

	private static Map<String, Predmet> ucitajPredmete(File file)
			throws IOException {
		Map<String, Predmet> predmeti = new HashMap<>();
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(
					new BufferedInputStream(new FileInputStream(file)),
					StandardCharsets.UTF_8));
			while (true) {
				String redak = br.readLine();
				if (redak == null)
					break;
				redak = redak.trim();
				if (redak.isEmpty())
					continue;
				String[] elems = redak.split("\t");
				if (elems.length != 2)
					continue;
				Predmet predmet = new Predmet(elems[0], elems[1]);
				predmeti.put(predmet.sifra, predmet);
			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception ignorable) {
				}
			}
		}
		return predmeti;
	}

	private static Map<String, Student> ucitajStudente(File file)
			throws IOException {
		Map<String, Student> studenti = new HashMap<>();
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(
					new BufferedInputStream(new FileInputStream(file)),
					StandardCharsets.UTF_8));
			while (true) {
				String redak = br.readLine();
				if (redak == null)
					break;
				redak = redak.trim();
				if (redak.isEmpty())
					continue;
				String[] elems = redak.split("\t");
				if (elems.length != 3)
					continue;
				Student s = new Student(elems[0], elems[1], elems[2]);
				studenti.put(s.jmbag, s);
			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception ignorable) {
				}
			}
		}
		return studenti;
	}

	static class Student {
		String jmbag;
		String prezime;
		String ime;

		public Student(String jmbag, String prezime, String ime) {
			super();
			this.jmbag = jmbag;
			this.prezime = prezime;
			this.ime = ime;
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

	static class Upis {
		Student student;
		Predmet predmet;

		public Upis(Student student, Predmet predmet) {
			super();
			this.student = student;
			this.predmet = predmet;
		}

	}

}
