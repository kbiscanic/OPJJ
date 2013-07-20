package hr.fer.zemris.java.tecaj_fajlovi;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class MyStablo {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("Očekivao sam direktorij!");
			System.exit(0);
		}

		Path dir = Paths.get(args[0]);
		
		Files.walkFileTree(dir, new Ispis());

	}
	
	static class Ispis implements FileVisitor<Path> {
		
		int razmaci;

		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) throws IOException {
			printRazmake();
			System.out.println(dir.getFileName());
			razmaci+=2;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			printRazmake();
			System.out.println(file.getFileName());
			return FileVisitResult.CONTINUE;
		}

		private void printRazmake() {
			for(int i = razmaci-1; i >=0; i--){
				System.out.print(" ");
			}
			
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc)
				throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc)
				throws IOException {
			razmaci -=2;
			return FileVisitResult.CONTINUE;
		}
		
	}

}
