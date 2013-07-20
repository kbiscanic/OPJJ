package hr.fer.zemris.java.filechecking;

import java.util.List;

public class DefCommand implements FCCommand {

	@Override
	public boolean isValidCall(boolean invertedCall, List<String> arguments,
			FCProgramChecker programChecker) {
		if (invertedCall) {
			return false;
		}
		if (arguments.size() != 2) {
			return false;
		}
		if (!programChecker.checkVariableName(arguments.get(0))) {
			return false;
		}
		if (programChecker.checkString(arguments.get(1)) == null) {
			return false;
		}

		programChecker.addVariable(arguments.get(0));
		return true;
	}

	@Override
	public FCStatus executeCommand(boolean invertedCall,
			List<String> arguments, FCFileVerifier fileVerifier) {

		String var = fileVerifier.getString(arguments.get(1));
		fileVerifier.addVariable(arguments.get(0), substitute(var));

		return FCStatus.PASS;
	}

	private String substitute(String original) {
		String[] splitted = original.split("[.:]");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < splitted.length; i++) {
			sb.append(splitted[i]).append("/");
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}
}
