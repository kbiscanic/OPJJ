package hr.fer.zemris.java.hw08.jvdraw;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public final class JvdExtensionFilter extends FileFilter {

	private static final JvdExtensionFilter INSTANCE = new JvdExtensionFilter();

	private JvdExtensionFilter() {
	};

	public static JvdExtensionFilter getInstance() {
		return INSTANCE;
	}

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		if (f.getAbsolutePath().endsWith(".jvd")) {
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return "JVD file";
	}

}
