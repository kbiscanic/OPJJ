package hr.fer.zemris.java.filechecking;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FCProgramChecker {

	private boolean hasErrors;
	private List<String> errors;
	private int currentLine;
	private String[] lines;
	private Map<String, FCCommand> commands;
	private Set<String> variables;
	private int brackets;

	public FCProgramChecker(String program) {
		lines = program.split("\n");
		hasErrors = false;
		currentLine = 0;
		errors = new LinkedList<String>();
		commands = new HashMap<>();
		variables = new HashSet<>();
		brackets = 0;
		initialize();
		findErrors();
	}

	private void initialize() {
		commands.put("terminate", new TerminateCommand());
		commands.put("fail", new FailCommand());
		commands.put("def", new DefCommand());
		commands.put("exists", new ExistsCommand());
		commands.put("filename", new FilenameCommand());
		commands.put("format", new FormatCommand());

		variables.add("jmbag");
		variables.add("lastName");
		variables.add("firstName");
	}

	private void findErrors() {
		for (String line : lines) {
			currentLine++;
			if (line.equals("}")) {
				if (brackets == 0) {
					registerError("Invalid } at line " + currentLine);
					continue;
				} else {
					brackets--;
					continue;
				}
			} else {
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

							if (j == splitted.length) {
								registerError("Invalid string at line "
										+ currentLine);
							}
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
				if (commands.containsKey(splitted[0])) {
					if (commands.get(splitted[0]).isValidCall(invertedCall,
							arguments, this)) {
					} else {
						registerError("Invalid call of " + splitted[0]
								+ " command at line " + currentLine);
					}
				} else {
					registerError("Unsupported command " + splitted[0]
							+ " at line " + currentLine);
				}

				if (splitted[splitted.length - 1].equals("{")) {
					brackets++;
				}
			}
		}

	}

	private void registerError(String string) {
		hasErrors = true;
		errors.add(string);
	}

	public boolean checkVariableName(String variable) {
		return variable.matches("[A-Za-z_\\.][A-Za-z0-9_\\.]*");
	}

	public void addVariable(String variableName) {
		variables.add(variableName);
	}

	public String checkString(String string) {
		if (string.matches("[i@]?\\\"[^$:\\\\{}]*\\\"")) {
			return string;
		} else if ((string.matches("[i@]\\\"[^$:\\\\{}]*\\\""))) {
			return string.substring(2, string.length() - 1);
		} else if (string
				.matches("[i@]?\\\"[^$:\\\\{}]*\\$\\{.*\\}[:]?[^$:\\\\{}]*\\\"")) {
			String[] splitted = string.split("\\$\\{");
			StringBuilder sb = new StringBuilder();
			sb.append(splitted[0]);
			for (int i = 1; i < splitted.length; i++) {
				String[] splittedIn = splitted[i].split("\\}");
				if (splittedIn.length != 2) {
					return null;
				}
				splittedIn[0] = splittedIn[0].trim();
				if (!checkVariableName(splittedIn[0])) {
					return null;
				}
				if (variables.contains(splittedIn[0])) {
					sb.append("$" + splittedIn[0]);
				} else {
					registerError("Variable " + splittedIn[0]
							+ " doesn't exist!");
					return null;
				}
				sb.append(splittedIn[1]);
			}
			return sb.toString();
		}
		return null;
	}

	public boolean hasErrors() {
		return hasErrors;
	}

	public List<String> errors() {
		return errors;
	}

}
