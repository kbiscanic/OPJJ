package hr.fer.zemris.java.tecaj.hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public interface ShellCommand {

	ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments);

}
