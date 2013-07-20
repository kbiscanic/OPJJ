package hr.fer.zemris.java.hw08.ljnotepadpp;

import hr.fer.zemris.java.hw08.ljnotepadpp.localization.ILocalizationProvider;
import hr.fer.zemris.java.hw08.ljnotepadpp.localization.LocalizableAction;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

/**
 * Action associated with Copy buttons. This action copies the selected text
 * into the system clipboard.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class Copy extends LocalizableAction {

	private static final long serialVersionUID = 758047463338518206L;
	private JNotepadPP parent;

	/**
	 * Public constructor for this action.
	 * 
	 * @param jNotepadPP
	 *            Reference to JNotepad++ program.
	 */
	public Copy(JNotepadPP jNotepadPP, ILocalizationProvider lp) {
		super("Copy", lp);
		parent = jNotepadPP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		copy();

	}

	/**
	 * Method used to copy selected text into System clipboard.
	 */
	private void copy() {
		String text = parent.textAreas
				.get(parent.tabbedPane.getSelectedIndex()).getSelectedText();
		if (text == null) {
			return;
		}
		StringSelection selection = new StringSelection(text);
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(selection, selection);
	}
}
