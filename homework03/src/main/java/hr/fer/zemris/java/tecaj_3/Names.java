package hr.fer.zemris.java.tecaj_3;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Solution to problem 2 of homework03.
 *
 * @author Kristijan Biscanic
 *
 */
public class Names {

	/**
	 * Main method for this program. Opens both files, reads them and displays
	 * all names that are in first, but aren't in second one.
	 *
	 * @param args
	 *            Names of files.
	 */
	public static void main(String[] args) {

		if (args.length != 2) {
			System.out
					.println("Program must be started with exactly 2 arguments!");
			System.exit(-1);
		}

		BufferedReader file1 = openFile(args[0]);
		BufferedReader file2 = openFile(args[1]);

		Set<String> file1Set = readFile(file1);
		Set<String> file2Set = readFile(file2);

		for (String element : file1Set) {
			if (!file2Set.contains(element)) {
				System.out.println(element);
			}
		}

		closeFile(file1);
		closeFile(file2);
	}

	/**
	 * Method used to open files into BufferedReader.
	 *
	 * @param filename
	 *            Name of file.
	 * @return BufferedReader of the given file.
	 */
	private static BufferedReader openFile(String filename) {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(
					new BufferedInputStream(new FileInputStream(filename)),
					"UTF-8"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			System.out.println("Unssuported encoding!");
			System.exit(-1);
		}
		return br;
	}

	/**
	 * Method used to read from given BufferedReader and store elements into
	 * {@link LinkedHashSet} collection.
	 * 
	 * @param file
	 *            Reader from file.
	 * @return {@link Set} of names.
	 */
	private static Set<String> readFile(BufferedReader file) {
		Set<String> set = new LinkedHashSet<String>();

		String line = null;
		while (true) {
			try {
				line = file.readLine();
			} catch (IOException e) {
				System.out.println("Could not read line!");
				System.exit(-1);
			}
			if (line == null) {
				break;
			}
			line = line.trim();
			if (line.isEmpty()) {
				continue;
			}
			set.add(line);
		}
		return set;
	}

	/**
	 * Method used to close opened BufferedReader.
	 * 
	 * @param file
	 *            {@link BufferedReader} that needs to be closed.
	 */
	private static void closeFile(BufferedReader file) {
		try {
			file.close();
		} catch (IOException e) {
			System.out.println("Could not close file!");
			System.exit(-1);
		}
	}
}
