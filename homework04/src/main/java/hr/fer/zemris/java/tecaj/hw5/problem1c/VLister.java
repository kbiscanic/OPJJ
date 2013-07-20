package hr.fer.zemris.java.tecaj.hw5.problem1c;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VLister {

	private static class SizeComparator implements Comparator<ExtensionInfo>,
			Serializable {

		private static final long serialVersionUID = 408896685125417018L;

		@Override
		public int compare(ExtensionInfo o1, ExtensionInfo o2) {
			return (int) (o1.totalSize - o2.totalSize);
		}

	}

	private static class NumberComparator implements Comparator<ExtensionInfo>,
			Serializable {

		private static final long serialVersionUID = -2469052405593974570L;

		@Override
		public int compare(ExtensionInfo o1, ExtensionInfo o2) {
			return o1.number - o2.number;
		}

	}

	private static class ExtensionComparator implements
			Comparator<ExtensionInfo>, Serializable {

		private static final long serialVersionUID = 3108684599207532648L;

		@Override
		public int compare(ExtensionInfo o1, ExtensionInfo o2) {
			return o1.extension.compareTo(o2.extension);
		}

	}

	private static class AverageComparator implements
			Comparator<ExtensionInfo>, Serializable {

		private static final long serialVersionUID = -2892624724800264233L;

		@Override
		public int compare(ExtensionInfo o1, ExtensionInfo o2) {
			return (int) (o1.averageExtensionSize() - o2.averageExtensionSize());
		}
	}

	private static class StatsGetter implements FileVisitor<Path> {

		private int numberFiles;
		private long totalFileSize;
		private int numberDirs;
		Map<String, ExtensionInfo> extensionMap = new HashMap<String, ExtensionInfo>();

		private double averageFileSize() {
			return numberFiles == 0 ? 0 : (double)totalFileSize / numberFiles;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir,
				BasicFileAttributes attrs) throws IOException {
			numberDirs++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			numberFiles++;
			File fil = new File(file.toString());
			totalFileSize += fil.length();

			String extension = getExt(fil.getName());
			if (extension == null) {
				return FileVisitResult.CONTINUE;
			}

			ExtensionInfo info = extensionMap.get(extension);
			if (info == null) {
				info = new ExtensionInfo(extension);
				extensionMap.put(extension, info);
			}

			info.number++;
			info.totalSize += fil.length();
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

		private static String getExt(String filename) {
			int dotIndex = filename.lastIndexOf('.');
			if (dotIndex <= 0) {
				return null;
			}
			String extension = filename.substring(dotIndex + 1);
			if (extension.isEmpty()) {
				return null;
			}
			return extension.toUpperCase();
		}
	}

	private static class ExtensionInfo {
		private String extension;
		private int number;
		private long totalSize;

		public ExtensionInfo(String extension) {
			super();
			this.extension = extension;
		}

		private double averageExtensionSize() {
			return number == 0 ? 0 : (double)totalSize / number;
		}

	}

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err
					.println("Program must be started with exactly 1 argument!");
			System.exit(0);
		}

		Path root = Paths.get(args[0]);

		StatsGetter stats = new StatsGetter();

		Files.walkFileTree(root, stats);

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in, StandardCharsets.UTF_8));

		String line = null;
		while (true) {
			System.out.println("Enter next command: ");
			line = reader.readLine();
			if (line == null) {
				break;
			}
			if (line.isEmpty()) {
				continue;
			}
			line = line.trim();
			if (line.equals("G")) {
				writeGeneral(stats);
			} else if (line.equals("E") || line.equals("!E")) {
				writeStats(stats, SortBy.E, line.charAt(0) == '!');
			} else if (line.equals("S") || line.equals("!S")) {
				writeStats(stats, SortBy.S, line.charAt(0) == '!');
			} else if (line.equals("N") || line.equals("!N")) {
				writeStats(stats, SortBy.N, line.charAt(0) == '!');
			} else if (line.equals("A") || line.equals("!A")) {
				writeStats(stats, SortBy.A, line.charAt(0) == '!');
			} else if (line.equals("Q")) {
				break;
			} else {
				continue;
			}
		}

	}

	private static void writeStats(StatsGetter stats, SortBy sort,
			boolean reverse) {
		System.out.format("|%-15s|%-20s|%-20s|%-20s|%n", "Extension",
				"Number of occurences", "Total file size", "Average file size");
		List<ExtensionInfo> extensionList = new ArrayList<>(
				stats.extensionMap.values());

		switch (sort) {
		case A:
			if (reverse) {
				Collections.sort(extensionList,
						Collections.reverseOrder(new AverageComparator()));
			} else {
				Collections.sort(extensionList, new AverageComparator());
			}
			break;
		case E:
			if (reverse) {
				Collections.sort(extensionList,
						Collections.reverseOrder(new ExtensionComparator()));
			} else {
				Collections.sort(extensionList, new ExtensionComparator());
			}
			break;
		case N:
			if (reverse) {
				Collections.sort(extensionList,
						Collections.reverseOrder(new NumberComparator()));
			} else {
				Collections.sort(extensionList, new NumberComparator());
			}
			break;
		case S:
			if (reverse) {
				Collections.sort(extensionList,
						Collections.reverseOrder(new SizeComparator()));
			} else {
				Collections.sort(extensionList, new SizeComparator());
			}
			break;
		}

		for (ExtensionInfo info : extensionList) {
			System.out.format("|%-15s|%-20s|%-20s|%-20s|%n", info.extension,
					info.number, info.totalSize, info.averageExtensionSize());
		}

	}

	private static void writeGeneral(StatsGetter stats) {
		System.out.println("Number of files: " + stats.numberFiles);
		System.out.println("Total size of all files: " + stats.totalFileSize);
		System.out
				.println("Average size of a file: " + stats.averageFileSize());
		System.out.println("Number of directories: " + stats.numberDirs);

	}

	private enum SortBy {
		E, S, N, A;
	}

}
