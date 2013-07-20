package hr.fer.zemris.java.hw07.jnotepadpp;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 * Action associated with Save buttons. This action saves current content of the
 * text area in the selected tab into the open file associated with that tab. If
 * there is no file open (Untitled document) saveAs is called instead.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class Save extends AbstractAction {

	private static final long serialVersionUID = -5173742455764839603L;
	private JNotepadPP parent;

	/**
	 * Public constructor for this action.
	 * 
	 * @param jNotepadPP
	 *            Reference to JNotepad++ program.
	 */
	public Save(JNotepadPP jNotepadPP) {
		super("Save");
		parent = jNotepadPP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		saveFile();

	}

	/**
	 * Method used to save the current file.
	 */
	private void saveFile() {
		File file = parent.openedFiles
				.get(parent.tabbedPane.getSelectedIndex());
		if (file == null) {
			parent.actionMap.get("saveas").actionPerformed(null);
			return;
		}

		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
			bw.write(parent.textAreas.get(parent.tabbedPane.getSelectedIndex())
					.getText());

		} catch (IOException e) {
			JOptionPane.showMessageDialog(parent, "Error saving file!",
					"Error!", JOptionPane.ERROR_MESSAGE);
		}

		parent.textChanged.set(parent.tabbedPane.getSelectedIndex(), false);
		parent.statuschanged(parent.tabbedPane.getSelectedIndex());

	}
}
