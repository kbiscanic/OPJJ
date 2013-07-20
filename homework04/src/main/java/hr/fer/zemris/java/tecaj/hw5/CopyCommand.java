package hr.fer.zemris.java.tecaj.hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) {
		try {
			if (arguments.length != 2) {
				out.write("copy command must be called with 2 arguments!");
				out.newLine();
				out.flush();
				return ShellStatus.CONTINUE;
			}
			File source = new File(arguments[0]);
			if (!source.isFile()) {
				out.write("Source must be file!");
				out.newLine();
				out.flush();
				return ShellStatus.CONTINUE;
			}
			File dest = new File(arguments[1]);
			if (dest.exists() && dest.isFile()) {
				out.write(arguments[1]
						+ " will be overwritten. Proceed? [y/n]:");
				out.flush();
				String answer = in.readLine();
				if (answer == null || !answer.matches("[Yy]")) {
					out.write("Stopping copy command!");
					out.newLine();
					out.flush();
					return ShellStatus.CONTINUE;
				}
			}
			Path destPath = dest.toPath();
			if (dest.isDirectory()) {
				Path destNew = Paths.get(dest.toString(), source.getName());
				destPath = Files.createFile(destNew);
			}
			Path sourcePath = source.toPath();
			OutputStream fileOut = new FileOutputStream(destPath.toFile());
			Files.copy(sourcePath, fileOut);
			fileOut.flush();

			out.write(source + " succesfully copied to: " + destPath.toString());
			out.newLine();
			out.flush();
		} catch (IOException e) {
			System.err.println("There was an IO Exception! Terminating...");
			System.exit(-1);
		}

		return ShellStatus.CONTINUE;
	}
}
