package hr.fer.zemris.java.hw07.jnotepadpp;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Action associated with Quit buttons. This action closes all open tabs and
 * exits the program.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class Quit extends AbstractAction {

	private static final long serialVersionUID = -9040240420335187905L;
	private JNotepadPP parent;

	/**
	 * Public constructor for this action.
	 * 
	 * @param jNotepadPP
	 *            Reference to JNotepad++ program.
	 */
	public Quit(JNotepadPP jNotepadPP) {
		super("Quit");
		parent = jNotepadPP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		quit();

	}

	/**
	 * Method used to quit the program.
	 */
	private void quit() {
		int ntabs = parent.tabbedPane.getTabCount();

		for (int i = 0; i < ntabs - 1; i++) {
			if (!parent.close(0, false)) {
				return;
			}
		}
		parent.close(0, true);

	}
}
