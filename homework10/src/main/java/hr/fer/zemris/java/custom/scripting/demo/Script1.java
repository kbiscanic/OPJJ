package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Program used to demonstrate usage on {@link SmartScriptEngine} on
 * "osnovni.smscr" script.
 */
public class Script1 {

	/**
	 * Main method for this program.
	 * 
	 * @param args
	 *            Unused.
	 * @throws IOException
	 *             If {@link IOException} happens.
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File("osnovni.smscr")),
				StandardCharsets.UTF_8));

		StringBuilder input = new StringBuilder();

		String line = null;
		while (true) {
			line = br.readLine();
			if (line == null) {
				break;
			}
			line = line.trim();
			input.append(line).append("\n");
		}
		br.close();
		String docBody = input.toString();

		Map<String, String> parameters = new HashMap<>();
		Map<String, String> persistentParameters = new HashMap<>();
		List<RCCookie> cookies = new ArrayList<>();

		new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(),
				new RequestContext(System.out, parameters,
						persistentParameters, cookies)).execute();
	}

}
