package hr.fer.zemris.java.tecaj_fajlovi;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class Stablo {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Očekivao sam direktorij!");
			System.exit(0);
		}

		Path dir = Paths.get(args[0]);

		Files.walkFileTree(dir, new Ispis());

	}

	static class Ispis implements FileVisitor<Path> {

		int indent = 0;

		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) throws IOException {

			String ispisati = null;

			if (indent == 0) {
				ispisati = dir.getFileName().toString();
			} else {
				ispisati = String.format("%" + indent + "s%s", "", dir
						.getFileName().toString());
			}
			System.out.println(ispisati);
			indent += 2;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			System.out.println(String.format("%" + indent + "s%s", "", file
					.getFileName().toString()));
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

}
