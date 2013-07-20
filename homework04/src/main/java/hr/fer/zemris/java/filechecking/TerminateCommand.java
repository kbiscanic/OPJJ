package hr.fer.zemris.java.filechecking;

import java.util.List;

public class TerminateCommand implements FCCommand {

	@Override
	public boolean isValidCall(boolean invertedCall, List<String> arguments,
			FCProgramChecker programChecker) {
		if (invertedCall) {
			return false;
		}
		if (arguments.size() != 0) {
			return false;
		}
		return true;
	}

	@Override
	public FCStatus executeCommand(boolean invertedCall,
			List<String> arguments, FCFileVerifier fileVerifier) {
		fileVerifier.terminate();
		return FCStatus.PASS;
	}

}
