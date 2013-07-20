package hr.fer.zemris.primjer.strategija;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Context class of strategy design pattern.
 */
public class ObradaDatoteke {

	/**
	 * Method doing actual work, using interface methods to get details.
	 * 
	 * @param fileName
	 *            Name of file.
	 * @param obrada
	 *            Concrete strategy used.
	 * @return Solution.
	 * @throws IOException
	 */
	public static <T> T obradi(String fileName, IObrada<T> obrada)
			throws IOException {
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
				if (elems.length != obrada.brojStupaca()) {
					System.out.println("Pogresan zapis!");
					continue;
				}
				obrada.obradiRedak(elems);
			}
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception ignorable) {
				}
			}
		}
		return obrada.dohvatiRezultat();
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

}
