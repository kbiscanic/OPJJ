package hr.fer.zemris.primjer.okvirnaMetoda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Program used to extract data from 3 txt files. This version is using template
 * method pattern.
 * 
 */
public class Glavni {

	/**
	 * Main method for this program. Excepts 3 txt files as arguments.
	 * 
	 * @param args
	 *            Paths to files.
	 * @throws IOException
	 */
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

	/**
	 * Method used to extract Students info from file to Map.
	 * 
	 * @param datoteka
	 *            File.
	 * @return Map of students.
	 * @throws IOException
	 */
	private static Map<String, Student> ucitajStudente(String datoteka)
			throws IOException {
		ObradaDatoteke<Map<String, Student>> obrada = new ObradaDatoteke<Map<String, Student>>(
				datoteka) {

			private Map<String, Student> mapa = new HashMap<>();

			@Override
			protected void obradiRedak(String[] elems) {
				mapa.put(elems[0], new Student(elems[0], elems[2], elems[1]));

			}

			@Override
			protected Map<String, Student> dohvatiRezultat() {
				return mapa;
			}

			@Override
			protected int brojStupaca() {
				return 3;
			}
		};
		return obrada.obradi();
	}

	/**
	 * Method used to extract Class info from file to Map.
	 * 
	 * @param datoteka
	 *            File.
	 * @return Map of classes.
	 * @throws IOException
	 */
	private static Map<String, Predmet> ucitajPredmete(String datoteka)
			throws IOException {
		ObradaDatoteke<Map<String, Predmet>> obrada = new ObradaDatoteke<Map<String, Predmet>>(
				datoteka) {

			private Map<String, Predmet> mapa = new HashMap<>();

			@Override
			protected void obradiRedak(String[] elems) {
				mapa.put(elems[0], new Predmet(elems[0], elems[1]));

			}

			@Override
			protected Map<String, Predmet> dohvatiRezultat() {
				return mapa;
			}

			@Override
			protected int brojStupaca() {
				return 2;
			}
		};
		return obrada.obradi();
	}

	/**
	 * 
	 * Method used to join Student and Class.
	 * 
	 * @param datoteka
	 *            File.
	 * @param studenti
	 *            Map of Students.
	 * @param predmeti
	 *            Map of Classes.
	 * @return List of joined students and classes.
	 * @throws IOException
	 */
	private static List<Upis> ucitajUpise(final String datoteka,
			final Map<String, Student> studenti,
			final Map<String, Predmet> predmeti) throws IOException {
		ObradaDatoteke<List<Upis>> obrada = new ObradaDatoteke<List<Upis>>(
				datoteka) {

			List<Upis> lista = new ArrayList<>();

			@Override
			protected void obradiRedak(String[] elems) {
				lista.add(new Upis(predmeti.get(elems[1]), studenti
						.get(elems[0])));

			}

			@Override
			protected List<Upis> dohvatiRezultat() {
				return lista;
			}

			@Override
			protected int brojStupaca() {
				return 2;
			}
		};

		return obrada.obradi();
	}

	/**
	 * Private class associating students with classes.
	 */
	static class Upis {
		Predmet predmet;
		Student student;

		/**
		 * Default constructor for Upis class.
		 * 
		 * @param predmet
		 *            Class.
		 * @param student
		 *            Student.
		 */
		public Upis(Predmet predmet, Student student) {
			super();
			this.predmet = predmet;
			this.student = student;
		}
	}

	/**
	 * Private class representing classes.
	 */
	static class Predmet {
		String sifra;
		String naziv;

		/**
		 * Default constructor for Predmet class.
		 * 
		 * @param sifra
		 *            Class number.
		 * @param naziv
		 *            Class name.
		 */
		public Predmet(String sifra, String naziv) {
			super();
			this.sifra = sifra;
			this.naziv = naziv;
		}
	}

	/**
	 * Private class representing students.
	 */
	static class Student {
		String jmbag;
		String ime;
		String prezime;

		/**
		 * Default constructor for Student class.
		 * 
		 * @param jmbag
		 *            Students JMBAG.
		 * @param ime
		 *            Students first name.
		 * @param prezime
		 *            Students last name.
		 */
		public Student(String jmbag, String ime, String prezime) {
			super();
			this.jmbag = jmbag;
			this.ime = ime;
			this.prezime = prezime;
		}
	}

}
