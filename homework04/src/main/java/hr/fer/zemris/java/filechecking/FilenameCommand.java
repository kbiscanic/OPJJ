package hr.fer.zemris.java.filechecking;

import java.util.List;

public class FilenameCommand implements FCCommand {

	@Override
	public boolean isValidCall(boolean invertedCall, List<String> arguments,
			FCProgramChecker programChecker) {
		if (arguments.size() != 1 && arguments.size() != 2) {
			return false;
		}
		if (programChecker.checkString(arguments.get(0)) == null) {
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
		if (!invertedCall) {
			if (arguments.get(0).charAt(0) == 'i') {
				if (fileVerifier.getString(arguments.get(0)).substring(1)
						.toUpperCase()
						.equals(fileVerifier.getFileName().toUpperCase())) {
					return FCStatus.PASS;
				}
				fail(arguments, fileVerifier);
				return FCStatus.FAIL;
			}

			if (fileVerifier.getString(arguments.get(0)).equals(
					fileVerifier.getFileName())) {
				return FCStatus.PASS;
			}
			fail(arguments, fileVerifier);
			return FCStatus.FAIL;
		} else {
			if (arguments.get(0).charAt(0) == 'i') {
				if (fileVerifier.getString(arguments.get(0)).substring(1)
						.toUpperCase()
						.equals(fileVerifier.getFileName().toUpperCase())) {
					fail(arguments, fileVerifier);
					return FCStatus.FAIL;
				}
				return FCStatus.PASS;
			}

			if (fileVerifier.getString(arguments.get(0)).equals(
					fileVerifier.getFileName())) {
				fail(arguments, fileVerifier);
				return FCStatus.FAIL;
			}
			return FCStatus.PASS;
		}
	}

	private void fail(List<String> arguments, FCFileVerifier fileVerifier) {
		if (arguments.size() == 1) {
			fileVerifier.registerError(fileVerifier.getFileName()
					+ " doesn't match expected filename ("
					+ (arguments.get(0).charAt(0) == 'i' ? fileVerifier
							.getString(arguments.get(0)).substring(1)
							: fileVerifier.getString(arguments.get(0))) + ")");

		} else {
			fileVerifier
					.registerError(fileVerifier.getString(arguments.get(1)));
		}

	}

}
