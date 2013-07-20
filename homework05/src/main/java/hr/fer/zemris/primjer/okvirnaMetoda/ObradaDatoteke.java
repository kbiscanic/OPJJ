package hr.fer.zemris.primjer.okvirnaMetoda;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Abstract class of template method design pattern.
 */
public abstract class ObradaDatoteke<T> {

	private String fileName;

	/**
	 * Default constructor from file name.
	 * 
	 * @param fileName
	 *            File name.
	 */
	public ObradaDatoteke(String fileName) {
		super();
		this.fileName = fileName;
	}

	/**
	 * Method doing actual work, using abstract methods to get details.
	 * 
	 * @return Solution.
	 * @throws IOException
	 */
	public T obradi() throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					fileName), StandardCharsets.UTF_8));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				line = ukloniKomentare(line);
				line = line.trim();
				if (line.isEmpty()) {
					continue;
				}
				String[] elems = line.split("\t");
				if (elems.length != brojStupaca()) {
					System.out.println("Pogresan zapis!");
					continue;
				}
				obradiRedak(elems);
			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception ignorable) {
				}
			}
		}
		return dohvatiRezultat();
	}

	/**
	 * Private method used to remove commentaries.
	 * 
	 * @param line
	 *            Line from which commentaries should be removed.
	 * @return String without commentaries.
	 */
	private static String ukloniKomentare(String line) {
		int pozicija = line.indexOf('%');
		if (pozicija >= 0) {
			line = line.substring(0, pozicija);
		}
		pozicija = line.indexOf('#');
		if (pozicija >= 0) {
			line = line.substring(0, pozicija);
		}
		if (line.contains("REM")) {
			line = "";
		}
		return line;

	}

	/**
	 * Abstract method telling how much columns are in the file.
	 * 
	 * @return Number of columns.
	 */
	protected abstract int brojStupaca();

	/**
	 * Abstract method telling what should be done with every line in file.
	 * 
	 * @param elems
	 *            Elements of that line.
	 */
	protected abstract void obradiRedak(String[] elems);

	/**
	 * Abstract method telling what should be returned as the solution.
	 * 
	 * @return Solution.
	 */
	protected abstract T dohvatiRezultat();

}
