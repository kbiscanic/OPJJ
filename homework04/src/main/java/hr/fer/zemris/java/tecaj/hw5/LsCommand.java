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
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LsCommand implements ShellCommand {

	private static class LsVisitor implements FileVisitor<Path> {

		private BufferedWriter out;
		private Path start;

		public LsVisitor(BufferedWriter out, Path start) {
			super();
			this.out = out;
			this.start = start;

		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) throws IOException {
			if (start.equals(dir)) {
				return FileVisitResult.CONTINUE;
			} else {
				analyzePath(dir, attrs);
				return FileVisitResult.SKIP_SUBTREE;
			}
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			analyzePath(file, attrs);
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
			return FileVisitResult.CONTINUE;
		}

		private void analyzePath(Path file, BasicFileAttributes attrs)
				throws IOException {
			File fileF = file.toFile();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			FileTime fileTime = attrs.creationTime();
			String formattedDateTime = sdf
					.format(new Date(fileTime.toMillis()));
			StringBuilder output = new StringBuilder();
			output.append(fileF.isDirectory() ? 'd' : '-');
			output.append(fileF.canRead() ? 'r' : '-');
			output.append(fileF.canWrite() ? 'w' : '-');
			output.append(fileF.canExecute() ? 'x' : '-');
			output.append(String.format("%10s ", fileF.length()));
			output.append(formattedDateTime);
			output.append(" " + fileF.getName());

			out.write(output.toString());
			out.newLine();
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
			LsVisitor visitor = new LsVisitor(out, dirPath);
			Files.walkFileTree(dirPath, visitor);
			out.flush();

		} catch (IOException ex) {
			System.err.println("There was an IOException! Terminating!");
			System.exit(-1);
		}

		return ShellStatus.CONTINUE;
	}

}
