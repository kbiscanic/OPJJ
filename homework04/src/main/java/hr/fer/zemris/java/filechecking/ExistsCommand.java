package hr.fer.zemris.java.filechecking;

import java.io.File;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ExistsCommand implements FCCommand {

	@Override
	public boolean isValidCall(boolean invertedCall, List<String> arguments,
			FCProgramChecker programChecker) {
		if (arguments.size() != 2 && arguments.size() != 3) {
			return false;
		}
		String s = arguments.get(0);
		if (!s.equals("d") && !s.equals("di") && !s.equals("dir")
				&& !s.equals("f") && s.equals("fi") && !s.equals("fil")
				&& !s.equals("file")) {
			return false;
		}
		if (programChecker.checkString(arguments.get(1)) == null) {
			return false;
		}

		if (arguments.size() == 3
				&& programChecker.checkString(arguments.get(2)) == null) {
			return false;
		}
		return true;
	}

	@Override
	public FCStatus executeCommand(boolean invertedCall,
			List<String> arguments, FCFileVerifier fileVerifier) {
		File file = new File(fileVerifier.getString(arguments.get(1)));
		ZipFile zipped = fileVerifier.getOpenedFile();
		if (zipped == null) {
			System.err.println("Cannot check existence!");
			return FCStatus.FAIL;
		}
		boolean found = searchFor(file, zipped);
		if (invertedCall) {
			if (!found) {
				return FCStatus.PASS;
			}
			if (arguments.size() == 3) {
				fileVerifier.registerError(fileVerifier.getString(arguments
						.get(2)));
			} else {
				fileVerifier.registerError(file.toString() + " exists!");
			}
			return FCStatus.FAIL;

		}
		if (found) {
			return FCStatus.PASS;
		} else {
			if (arguments.size() == 3) {
				fileVerifier.registerError(fileVerifier.getString(arguments
						.get(2)));
			} else {
				fileVerifier
						.registerError(file.toString() + " doesn't exists!");
			}
			return FCStatus.FAIL;
		}

	}

	private boolean searchFor(File file, ZipFile zipped) {
		ZipEntry entry = zipped.getEntry(file.toString());
		return entry != null;
	}

}
