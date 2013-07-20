package hr.fer.zemris.java.filechecking.demo;

import hr.fer.zemris.java.filechecking.FCFileVerifier;
import hr.fer.zemris.java.filechecking.FCProgramChecker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipException;

public class FCDemo {

	public static void main(String[] args) throws ZipException, IOException {
		if (args.length != 3) {
			System.err
					.println("FCDemo must be called with exactly 3 arguments! Terminating...");
			System.exit(-1);
		}
		File file = new File(args[0]);
		if (!file.isFile()) {
			System.err.println(args[0]
					+ " isn't a file or doesn't exist! Terminating...");
			System.exit(-1);
		}
		String fileName = args[1];
		String program = read(args[2]);

		FCProgramChecker checker = new FCProgramChecker(program);
		if (checker.hasErrors()) {
			System.out.println("Given program contains errors!");
			for (String error : checker.errors()) {
				System.out.println(error);
			}
			System.out.println("Terminating...");
			System.exit(0);
		}

		Map<String, String> initialData = new HashMap<String, String>();
		initialData.put("jmbag", "0012345678");
		initialData.put("lastName", "Peric");
		initialData.put("firstName", "Pero");

		FCFileVerifier verifier = new FCFileVerifier(file, fileName, program,
				initialData);

		if (verifier.hasErrors()) {
			System.out.println("Upload declined! File contains errors:");
			for (String error : verifier.errors()) {
				System.out.println(error);
			}
		} else {
			System.out.println("Upload accepted.");
		}

	}

	private static String read(String fileName) {
		File file = new File(fileName);
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), StandardCharsets.UTF_8));

			String line;
			while (true) {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				line = line.trim();
				if (line.isEmpty()) {
					continue;
				}
				if (line.charAt(0) == '#') {
					continue;
				}
				builder.append(line).append(String.format("%n"));
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println("Program not found! Terminating...");
			System.exit(-1);
		} catch (IOException e) {
			System.err
					.println("IOException while reading program! Terminating...");
			System.exit(-1);
		}

		return builder.toString();
	}
}
