package hr.fer.zemris.java.hw08.ljnotepadpp;

import hr.fer.zemris.java.hw08.ljnotepadpp.localization.ILocalizationProvider;
import hr.fer.zemris.java.hw08.ljnotepadpp.localization.LocalizableAction;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;

/**
 * Action associated with Cut buttons. This action copies the selected text into
 * the system clipboard and deletes it from text area.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class Cut extends LocalizableAction {

	private static final long serialVersionUID = 7509880457699167702L;
	private JNotepadPP parent;

	/**
	 * Public constructor for this action.
	 * 
	 * @param jNotepadPP
	 *            Reference to JNotepad++ program.
	 */
	public Cut(JNotepadPP jNotepadPP, ILocalizationProvider lp) {
		super("Cut", lp);
		parent = jNotepadPP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		cut();

	}

	/**
	 * Method used to copy selected text into System clipboard and delete it
	 * form text area.
	 */
	private void cut() {
		JTextArea textArea = parent.textAreas.get(parent.tabbedPane
				.getSelectedIndex());
		String text = textArea.getSelectedText();
		if (text == null) {
			return;
		}
		StringSelection selection = new StringSelection(text);
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(selection, selection);
		textArea.replaceSelection("");

	}
}
