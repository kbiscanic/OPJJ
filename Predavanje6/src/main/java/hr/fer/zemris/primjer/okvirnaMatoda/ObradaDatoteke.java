package hr.fer.zemris.primjer.okvirnaMatoda;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public abstract class ObradaDatoteke<T> {

	private String fileName;

	public ObradaDatoteke(String fileName) {
		super();
		this.fileName = fileName;
	}

	public T obradi() throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					fileName), StandardCharsets.UTF_8));
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				line = ukloniKomentare(line);
				line = line.trim();
				if (line.isEmpty())
					continue;
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

	protected abstract int brojStupaca();

	protected abstract void obradiRedak(String[] elems);

	protected abstract T dohvatiRezultat();

}
