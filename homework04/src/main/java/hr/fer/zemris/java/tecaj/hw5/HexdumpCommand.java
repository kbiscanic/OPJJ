package hr.fer.zemris.java.tecaj.hw5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class HexdumpCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(BufferedReader in, BufferedWriter out,
			String[] arguments) {
		try {
			if (arguments.length != 1) {
				out.write("hexdump command must be called with 1 argument!");
				out.newLine();
				out.flush();
				return ShellStatus.CONTINUE;
			}
			Path file = Paths.get(arguments[0]);
			if (!file.toFile().isFile()) {
				out.write(file + " must be a file!");
				out.newLine();
				out.flush();
				return ShellStatus.CONTINUE;
			}

			InputStream fileInput = Files.newInputStream(file,
					StandardOpenOption.READ);
			byte[] buffer = new byte[16];
			int counter = 0;
			int readBytes;
			while ((readBytes = fileInput.read(buffer)) > 0) {

				while (readBytes != 16) {
					int readNow = fileInput.read(buffer, readBytes,
							16 - readBytes);
					if (readNow < 1) {
						break;
					} else {
						readBytes += readNow;
					}
				}
				StringBuilder hexOutput = new StringBuilder();
				StringBuilder zeroes = new StringBuilder();
				StringBuilder stringOutput = new StringBuilder();
				for (int i = 8 - Integer.toHexString(counter).length(); i > 0; i--) {
					zeroes.append("0");
				}
				hexOutput.append(String.format("%s%s", zeroes.toString(),
						Integer.toHexString(counter)) + ": ");

				for (int i = 0; i < readBytes; i++) {
					int down = buffer[i] & 0xf;
					int up = buffer[i] >> 4;
					up = up & 0xf;
					hexOutput.append(Integer.toHexString(up).toUpperCase())
							.append(Integer.toHexString(down).toUpperCase());
					if (Integer.valueOf(buffer[i]) < 32
							|| Integer.valueOf(buffer[i] & 0xff) > 127) {
						stringOutput.append('.');
					} else {
						stringOutput.append((char) buffer[i]);
					}
					if (i == 7) {
						hexOutput.append('|');
					} else {
						hexOutput.append(' ');
					}
				}
				if (readBytes < 8) {
					hexOutput.append(String.format("%"
							+ ((8 - readBytes) * 3 - 1) + "s|", " "));
					readBytes = 8;
				}
				if (readBytes < 16) {
					hexOutput.append(String.format("%" + ((16 - readBytes) * 3)
							+ "s", " "));
				}
				hexOutput.append("| ").append(stringOutput.toString());
				out.write(hexOutput.toString());
				out.newLine();
				counter += 16;

			}

			out.flush();
		} catch (IOException e) {
			System.err.println("There was an IO Exception! Terminating!");
			System.exit(-1);
		}

		return ShellStatus.CONTINUE;
	}
}
