package hr.fer.zemris.java.tecaj.hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class TreeCommand implements ShellCommand {

	private static class TreeVisitor implements FileVisitor<Path> {

		private BufferedWriter out;
		private int indent;

		public TreeVisitor(BufferedWriter out) {
			super();
			this.out = out;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) throws IOException {
			String output = null;

			if (indent == 0) {
				output = dir.getFileName().toString();
			} else {
				output = String.format("%" + indent + "s%s", "", dir
						.getFileName().toString());
			}
			out.write(output);
			out.newLine();
			indent += 2;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			out.write(String.format("%" + indent + "s%s", "", file
					.getFileName().toString()));
			out.newLine();
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc)
				throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc)
				throws IOException {
			indent -= 2;
			return FileVisitResult.CONTINUE;
		}

	}

	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) {

		try {
			if (arguments.length != 1) {
				out.write("Illegal command call! No argument provided!");
				out.newLine();
				out.flush();
				return ShellStatus.CONTINUE;
			}

			File dir = new File(arguments[0]);
			if (!dir.isDirectory()) {
				out.write(arguments[0] + " isn't a directory!");
				out.newLine();
				out.flush();
				return ShellStatus.CONTINUE;
			}
			Path dirPath = Paths.get(arguments[0]);
			TreeVisitor visitor = new TreeVisitor(out);
			Files.walkFileTree(dirPath, visitor);
			out.flush();

		} catch (IOException ex) {
			System.err.println("There was an IOException! Terminating!");
			System.exit(-1);
		}

		return ShellStatus.CONTINUE;
	}

}
