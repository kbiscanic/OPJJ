package hr.fer.zemris.java.tecaj.hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.SortedMap;

public class CharsetsCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) {
		try {
			if (arguments.length != 0) {
				out.write("charsets command takes no arguments!");
				out.newLine();
				out.flush();
				return ShellStatus.CONTINUE;
			}
			SortedMap<String, Charset> map = Charset.availableCharsets();
			for (String s : map.keySet()) {
				out.write(s);
				out.newLine();
				out.flush();
			}
		} catch (IOException e) {
			System.err.println("There was an IO Exception! Terminating!");
			System.exit(-1);
		}

		return ShellStatus.CONTINUE;
	}

}
