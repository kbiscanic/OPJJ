package hr.fer.zemris.java.hw08.ljnotepadpp;

import hr.fer.zemris.java.hw08.ljnotepadpp.localization.ILocalizationProvider;
import hr.fer.zemris.java.hw08.ljnotepadpp.localization.LocalizableAction;

import java.awt.event.ActionEvent;

/**
 * Action associated with Quit buttons. This action closes all open tabs and
 * exits the program.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class Quit extends LocalizableAction {

	private static final long serialVersionUID = -9040240420335187905L;
	private JNotepadPP parent;

	/**
	 * Public constructor for this action.
	 * 
	 * @param jNotepadPP
	 *            Reference to JNotepad++ program.
	 */
	public Quit(JNotepadPP jNotepadPP, ILocalizationProvider lp) {
		super("Quit", lp);
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
