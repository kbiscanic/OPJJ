package hr.fer.zemris.java.hw08.ljnotepadpp;

import hr.fer.zemris.java.hw08.ljnotepadpp.localization.ILocalizationProvider;
import hr.fer.zemris.java.hw08.ljnotepadpp.localization.LocalizableAction;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Action associated with Open buttons. This action opens an existing file in
 * new tab.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class Open extends LocalizableAction {

	private static final long serialVersionUID = -3950705068511633163L;
	private JNotepadPP parent;

	/**
	 * Public constructor for this action.
	 * 
	 * @param jNotepadPP
	 *            Reference to JNotepad++ program.
	 */
	public Open(JNotepadPP jNotepadPP, ILocalizationProvider lp) {
		super("Open", lp);
		parent = jNotepadPP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		openFile();

	}

	/**
	 * Method used to open an existing file in new tab.
	 */
	private void openFile() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int returnVal = fc.showOpenDialog(parent);

		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File file = fc.getSelectedFile();
		if (parent.openedFiles.contains(file)) {
			parent.tabbedPane
					.setSelectedIndex(parent.openedFiles.indexOf(file));
			return;
		}

		JTextArea textArea = new JTextArea();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), StandardCharsets.UTF_8));) {

			String line = br.readLine();
			while (line != null) {
				textArea.append(line + "\n");
				line = br.readLine();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(parent, "Error opening file!",
					"Error!", JOptionPane.ERROR_MESSAGE);
			return;
		}

		parent.settingsTA(textArea);
		parent.openedFiles.add(file);
		parent.textAreas.add(textArea);
		parent.textChanged.add(false);
		parent.tabbedPane.add(file.getName(), new JScrollPane(textArea));
		parent.tabbedPane.setSelectedIndex(parent.openedFiles.indexOf(file));

		if (parent.tabbedPane.getTabCount() > 1) {
			parent.actionMap.get("close").setEnabled(true);
		}

	}

}
