package hr.fer.zemris.java.filechecking;

import java.util.List;

public class FailCommand implements FCCommand {

	@Override
	public boolean isValidCall(boolean invertedCall, List<String> arguments,
			FCProgramChecker programChecker) {
		if (invertedCall) {
			return false;
		}
		if (arguments.size() == 0) {
			return true;
		}
		if (arguments.size() == 1
				&& programChecker.checkString(arguments.get(0)) != null) {
			return true;
		}
		return false;
	}

	@Override
	public FCStatus executeCommand(boolean invertedCall,
			List<String> arguments, FCFileVerifier fileVerifier) {
		if (arguments.size() == 0) {
			fileVerifier.registerError("Fail command executed!");
			return FCStatus.FAIL;
		}
		fileVerifier.registerError(fileVerifier.getString(arguments.get(0)));
		return FCStatus.FAIL;
	}

}
