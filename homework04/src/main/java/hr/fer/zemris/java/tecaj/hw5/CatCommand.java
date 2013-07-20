package hr.fer.zemris.java.tecaj.hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

public class CatCommand implements ShellCommand {

	private static final int BUFFERSIZE = 4096;

	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) {

		try {
			if (arguments.length != 1 && arguments.length != 2) {
				out.write("Illegal command call! Please provide at least 1 argument!");
				out.newLine();
				out.flush();
				return ShellStatus.CONTINUE;
			}
			File file = new File(arguments[0]);
			BufferedReader fileInput;
			try {
				if (arguments.length == 2) {

					Charset cs;
					try {
						cs = Charset.forName(arguments[1]);
					} catch (UnsupportedCharsetException cse) {
						out.write("There is no " + arguments[1] + " charset!");
						out.newLine();
						out.flush();
						return ShellStatus.CONTINUE;
					}
					fileInput = new BufferedReader(new InputStreamReader(
							new FileInputStream(file), cs));
				} else {
					fileInput = new BufferedReader(new InputStreamReader(
							new FileInputStream(file), StandardCharsets.UTF_8));
				}
			} catch (FileNotFoundException fnfe) {
				out.write("No such file!");
				out.newLine();
				out.flush();
				return ShellStatus.CONTINUE;
			}
			int count;
			char[] buffer = new char[BUFFERSIZE];
			while ((count = fileInput.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
			out.newLine();
			out.flush();

			fileInput.close();
		} catch (IOException e) {
			System.err.println("There was an IOException! Terminating!");
			System.exit(-1);
		}

		return ShellStatus.CONTINUE;
	}
}
