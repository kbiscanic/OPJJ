package hr.fer.zemris.java.tecaj.hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class SymbolCommand implements ShellCommand {

	private SymbolsContainer symbols;

	public SymbolCommand(SymbolsContainer symbols) {
		super();
		this.symbols = symbols;
	}

	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) {

		try {

			if (arguments.length != 1 && arguments.length != 2) {
				out.write("Illegal command call! You must provide 1 or 2 arguments!");
				out.newLine();
				out.flush();
				return ShellStatus.CONTINUE;

			}

			if (arguments[0].toUpperCase().matches("PROMPT")) {
				if (arguments.length == 1) {
					out.write("Symbol for PROMPT is '"
							+ symbols.getPromptSymbol() + "'");
					out.newLine();
					out.flush();
				} else {
					if (arguments[1].length() == 1) {
						char oldSym = symbols.getPromptSymbol();
						symbols.setPromptSymbol(arguments[1].charAt(0));
						out.write("Symbol for PROMPT changed from '" + oldSym
								+ "' to '" + symbols.getPromptSymbol() + "'");
						out.newLine();
						out.flush();
					}
				}
			} else if (arguments[0].toUpperCase().matches("MORELINES")) {
				if (arguments.length == 1) {
					out.write("Symbol for MORELINES is '"
							+ symbols.getMoreLinesSymbol() + "'");
					out.newLine();
					out.flush();
				} else {
					if (arguments[1].length() == 1) {
						char oldSym = symbols.getMoreLinesSymbol();
						symbols.setMoreLinesSymbol(arguments[1].charAt(0));
						out.write("Symbol for MORELINES changed from '"
								+ oldSym + "' to '"
								+ symbols.getMoreLinesSymbol() + "'");
						out.newLine();
						out.flush();
					}
				}
			} else if (arguments[0].toUpperCase().matches("MULTILINE")) {
				if (arguments.length == 1) {
					out.write("Symbol for MULTILINE is '"
							+ symbols.getMultiLinesSymbol() + "'");
					out.newLine();
					out.flush();
				} else {
					if (arguments[1].length() == 1) {
						char oldSym = symbols.getMultiLinesSymbol();
						symbols.setMultiLinesSymbol(arguments[1].charAt(0));
						out.write("Symbol for MULTILINE changed from '"
								+ oldSym + "' to '"
								+ symbols.getMultiLinesSymbol() + "'");
						out.newLine();
						out.flush();
					}
				}
			} else {
				out.write("There is no " + arguments[0].toUpperCase()
						+ " symbol!");
				out.newLine();
				out.flush();
			}
		} catch (IOException e) {
			System.err.println("There was an IO exception!");
			System.exit(-1);
		}
		return ShellStatus.CONTINUE;

	}
}
