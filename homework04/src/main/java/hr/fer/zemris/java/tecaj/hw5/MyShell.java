package hr.fer.zemris.java.tecaj.hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MyShell {

	public static void main(String[] args) {

		try {

			SymbolsContainer symbols = new SymbolsContainer();

			Map<String, ShellCommand> commands = new HashMap<String, ShellCommand>();
			commands.put("symbol", new SymbolCommand(symbols));
			commands.put("exit", new ExitCommand());
			commands.put("charsets", new CharsetsCommand());
			commands.put("cat", new CatCommand());
			commands.put("ls", new LsCommand());
			commands.put("tree", new TreeCommand());
			commands.put("copy", new CopyCommand());
			commands.put("mkdir", new MkdirCommand());
			commands.put("hexdump", new HexdumpCommand());

			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in, StandardCharsets.UTF_8));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					System.out, StandardCharsets.UTF_8));

			out.write("Welcome to MyShell v 1.0");
			out.newLine();
			out.flush();

			ShellStatus status = ShellStatus.CONTINUE;
			String line = null;
			while (status == ShellStatus.CONTINUE) {
				out.write(symbols.getPromptSymbol() + " ");
				out.flush();
				line = in.readLine();
				if (line == null) {
					continue;
				}
				line = line.trim();
				while (line.charAt(line.length() - 1) == symbols
						.getMoreLinesSymbol()) {
					line = line.substring(0, line.length() - 1);
					out.write(symbols.getMultiLinesSymbol() + " ");
					out.flush();
					String line2 = in.readLine();
					if (line2 == null) {
						continue;
					}
					line = line.concat(line2.trim());
					line = line.trim();
				}

				String[] splitted = line.split("\\s\\s*");
				String[] arguments = new String[splitted.length - 1];
				for (int i = 1; i < splitted.length; i++) {
					arguments[i - 1] = splitted[i];
				}
				if (commands.containsKey(splitted[0])) {
					status = commands.get(splitted[0]).executeCommand(in, out,
							arguments);
				} else {
					out.write("Unsupported command!");
					out.newLine();
					out.flush();
				}
			}
		} catch (IOException e) {
			System.err.println("There was an IO exception! Terminating!");
			System.exit(-1);
		}
	}
}
