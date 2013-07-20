package hr.fer.zemris.java.tecaj.hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MkdirCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) {
		try {
			if (arguments.length != 1) {
				out.write("mkdir command must be called with 1 argument!");
				out.newLine();
				out.flush();
				return ShellStatus.CONTINUE;
			}
			Path newFolder = Paths.get(arguments[0]);
			if (newFolder.toFile().exists()) {
				out.write(newFolder + " already exists!");
				out.newLine();
				out.flush();
				return ShellStatus.CONTINUE;
			}
			Files.createDirectories(newFolder);
			out.write(newFolder + " succesfuly created!");
			out.newLine();
			out.flush();
		} catch (IOException e) {
			System.err.println("There was an IO Exception! Terminating!");
			System.exit(-1);
		}

		return ShellStatus.CONTINUE;
	}
}
