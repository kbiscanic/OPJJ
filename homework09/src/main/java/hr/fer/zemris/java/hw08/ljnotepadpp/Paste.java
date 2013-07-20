package hr.fer.zemris.java.hw08.ljnotepadpp;

import hr.fer.zemris.java.hw08.ljnotepadpp.localization.ILocalizationProvider;
import hr.fer.zemris.java.hw08.ljnotepadpp.localization.LocalizableAction;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JTextArea;

/**
 * Action associated with Paste buttons. This action pastes text from the system
 * clipboard to a text area.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class Paste extends LocalizableAction {

	private static final long serialVersionUID = -1178517723679097437L;
	private JNotepadPP parent;

	/**
	 * Public constructor for this action.
	 * 
	 * @param jNotepadPP
	 *            Reference to JNotepad++ program.
	 */
	public Paste(JNotepadPP jNotepadPP, ILocalizationProvider lp) {
		super("Paste", lp);
		parent = jNotepadPP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		paste();

	}

	/**
	 * Method used to paste text from the system clipboard to a text area.
	 */
	private void paste() {
		Transferable content = Toolkit.getDefaultToolkit().getSystemClipboard()
				.getContents(null);
		if (content == null
				|| !content.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			return;
		}
		String text = null;
		try {
			text = (String) content.getTransferData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException e) {
		}
		if (text == null) {
			return;
		}

		JTextArea textArea = parent.textAreas.get(parent.tabbedPane
				.getSelectedIndex());
		if (textArea.getSelectedText() == null) {
			textArea.insert(text, textArea.getCaretPosition());
		} else {
			textArea.replaceSelection(text);
		}

	}
}
