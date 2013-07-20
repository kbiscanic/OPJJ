package hr.fer.zemris.java.hw08.ljnotepadpp;

import hr.fer.zemris.java.hw08.ljnotepadpp.localization.ILocalizationProvider;
import hr.fer.zemris.java.hw08.ljnotepadpp.localization.LocalizableAction;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Action associated with SaveAs buttons. This action saves the current content
 * of the text area in the currently selected tab into the file selected by
 * user. If file already exists, option pane is called to confirm overwriting.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class SaveAs extends LocalizableAction {

	private static final long serialVersionUID = -2372473716426731534L;
	private JNotepadPP parent;

	/**
	 * Public constructor for this action.
	 * 
	 * @param jNotepadPP
	 *            Reference to JNotepad++ program.
	 */
	public SaveAs(JNotepadPP jNotepadPP, ILocalizationProvider lp) {
		super("SaveAs", lp);
		parent = jNotepadPP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		saveFileAs();

	}

	/**
	 * Method used to save the current file as...
	 */
	private void saveFileAs() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int returnVal = fc.showSaveDialog(parent);

		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File file = fc.getSelectedFile();
		if (file.exists()) {
			int choice = JOptionPane.showOptionDialog(parent,
					"File " + file.getName()
							+ " already exists. Do you want to replace it?",
					"Replace " + file.getName() + "?",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
					null, null, null);
			if (choice != JOptionPane.YES_OPTION) {
				return;
			}
		}

		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
			bw.write(parent.textAreas.get(parent.tabbedPane.getSelectedIndex())
					.getText());

		} catch (IOException e) {
			JOptionPane.showMessageDialog(parent, "Error saving file!",
					"Error!", JOptionPane.ERROR_MESSAGE);
		}

		parent.openedFiles.add(parent.tabbedPane.getSelectedIndex(), file);
		parent.tabbedPane.setTitleAt(parent.tabbedPane.getSelectedIndex(),
				file.getName());
		parent.setTitle(file.getAbsolutePath() + " - JNotepad++");
		parent.textChanged.set(parent.tabbedPane.getSelectedIndex(), false);

	}
}
