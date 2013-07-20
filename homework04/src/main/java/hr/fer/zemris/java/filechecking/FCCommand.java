package hr.fer.zemris.java.filechecking;

import java.util.List;

public interface FCCommand {

	boolean isValidCall(boolean invertedCall, List<String> arguments,
			FCProgramChecker programChecker);

	FCStatus executeCommand(boolean invertedCall, List<String> arguments,
			FCFileVerifier fileVerifier);
}
