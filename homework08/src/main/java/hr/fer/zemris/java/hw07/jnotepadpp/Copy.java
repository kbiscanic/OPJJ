package hr.fer.zemris.java.hw07.jnotepadpp;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Action associated with Copy buttons. This action copies the selected text
 * into the system clipboard.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class Copy extends AbstractAction {

	private static final long serialVersionUID = 758047463338518206L;
	private JNotepadPP parent;

	/**
	 * Public constructor for this action.
	 * 
	 * @param jNotepadPP
	 *            Reference to JNotepad++ program.
	 */
	public Copy(JNotepadPP jNotepadPP) {
		super("Copy");
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
