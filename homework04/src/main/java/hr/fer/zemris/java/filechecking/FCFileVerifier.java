package hr.fer.zemris.java.filechecking;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class FCFileVerifier {

	private String fileName;
	private boolean hasErrors;
	private List<String> errors;
	private String[] lines;
	private Map<String, FCCommand> commands;
	private Map<String, String> variables;
	private Stack<Integer> brackets;
	private boolean stackBlocked;
	private int stackBlocker;
	private boolean terminate;
	private ZipFile openedFile;

	public FCFileVerifier(File file, String fileName, String program,
			Map<String, String> initialData) throws IOException {
		lines = program.split("\n");
		hasErrors = false;
		errors = new LinkedList<>();
		commands = new HashMap<>();
		variables = new HashMap<>(initialData);
		brackets = new Stack<>();
		stackBlocked = false;
		stackBlocker = 0;
		this.fileName = fileName;
		if (file.exists()) {
			try {
				openedFile = new ZipFile(file);
			} catch (ZipException e) {
				openedFile = null;
			}
		} else {
			System.err.println(file.getName() + "doesn't exist! Terminating..");
			System.exit(-1);
		}

		addCommands();
		verify();
	}

	public ZipFile getOpenedFile() {
		return openedFile;
	}

	private void addCommands() {
		commands.put("terminate", new TerminateCommand());
		commands.put("fail", new FailCommand());
		commands.put("def", new DefCommand());
		commands.put("exists", new ExistsCommand());
		commands.put("filename", new FilenameCommand());
		commands.put("format", new FormatCommand());
	}

	private void verify() {
		for (String line : lines) {
			if (terminate) {
				break;
			}
			if (line.equals("}")) {
				Integer pop = brackets.pop();
				if (stackBlocked && pop.equals(stackBlocker)) {
					stackBlocked = false;
				}
			} else {
				if (stackBlocked) {
					continue;
				}
				String[] splitted = line.split("\\s\\s*");
				List<String> arguments = new LinkedList<>();
				for (int i = 1; i < splitted.length; i++) {
					if (splitted[i].matches("[i@]?\\\".*")) {
						StringBuilder sb = new StringBuilder();
						int j = i;
						sb.append(splitted[i]);
						while (splitted[j].charAt(splitted[j].length() - 1) != '\"') {
							j++;
							sb.append(" " + splitted[j]);

						}
						arguments.add(sb.toString());
						i = j;
					} else if (splitted[i].equals("{")) {
						continue;
					} else {
						arguments.add(splitted[i]);
					}
				}
				boolean invertedCall = false;
				if (splitted[0].charAt(0) == '!') {
					splitted[0] = splitted[0].substring(1);
					invertedCall = true;
				}
				FCStatus testResult = commands.get(splitted[0]).executeCommand(
						invertedCall, arguments, this);

				if (splitted[splitted.length - 1].equals("{")) {
					if (brackets.isEmpty()) {
						brackets.push(0);
					} else {
						brackets.push(brackets.peek() + 1);
					}
					if (testResult == FCStatus.FAIL) {
						stackBlocked = true;
						stackBlocker = brackets.peek();
					}
				}
			}
		}
	}

	public boolean hasErrors() {
		return hasErrors;
	}

	public List<String> errors() {
		return errors;
	}

	public void registerError(String string) {
		hasErrors = true;
		errors.add(string);
	}

	public void addVariable(String variableName, String value) {
		variables.put(variableName, value);
	}

	public String getFileName() {
		return fileName;
	}

	public String getString(String string) {
		if (string.matches("\\\"[^$:\\\\{}]*\\\"")) {
			return string.substring(1, string.length() - 1);
		} else if ((string.matches("[i@]\\\"[^$:\\\\{}]*\\\""))) {
			return string.substring(2, string.length() - 1);
		} else if (string
				.matches("[i@]?\\\"[^$:\\\\{}]*\\$\\{.*\\}[:]?[^$:\\\\{}]*\\\"")) {
			String[] splitted = string.split("\\$\\{");
			StringBuilder sb = new StringBuilder();
			sb.append(splitted[0]);
			for (int i = 1; i < splitted.length; i++) {
				String[] splittedIn = splitted[i].split("\\}");
				splittedIn[0] = splittedIn[0].trim();
				sb.append(variables.get(splittedIn[0]));
				sb.append(splittedIn[1]);
			}
			return sb.toString().substring(1, sb.length() - 1);
		}
		return null;
	}

	public void terminate() {
		this.terminate = true;
	}

}
