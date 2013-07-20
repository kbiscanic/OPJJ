package hr.fer.zemris.java.filechecking;

import java.util.List;

public class FormatCommand implements FCCommand {

	@Override
	public boolean isValidCall(boolean invertedCall, List<String> arguments,
			FCProgramChecker programChecker) {
		if (arguments.size() != 1 && arguments.size() != 2) {
			return false;
		}
		if (!programChecker.checkVariableName(arguments.get(0))) {
			return false;
		}
		if (arguments.size() == 2) {
			if (programChecker.checkString(arguments.get(1)) == null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public FCStatus executeCommand(boolean invertedCall,
			List<String> arguments, FCFileVerifier fileVerifier) {
		if (invertedCall) {
			if (arguments.get(0).toUpperCase().equals("ZIP")
					&& fileVerifier.getOpenedFile() != null) {
				if (arguments.size() == 1) {
					fileVerifier.registerError("File is in ZIP format!");
				} else {
					fileVerifier.registerError(fileVerifier.getString(arguments
							.get(1)));
					return FCStatus.FAIL;
				}
				return FCStatus.FAIL;
			}
			return FCStatus.PASS;
		}
		if (arguments.get(0).toUpperCase().equals("ZIP")
				&& fileVerifier.getOpenedFile() != null) {
			return FCStatus.PASS;
		}
		if (arguments.size() == 1) {
			fileVerifier.registerError("Isn't a ZIP!");
		} else {
			fileVerifier
					.registerError(fileVerifier.getString(arguments.get(1)));
		}
		return FCStatus.FAIL;

	}
}
