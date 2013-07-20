package hr.fer.zemris.java.hw08.ljnotepadpp;

import hr.fer.zemris.java.hw08.ljnotepadpp.localization.ILocalizationProvider;
import hr.fer.zemris.java.hw08.ljnotepadpp.localization.LocalizableAction;

import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Action associated with New buttons. This action creates new tab with empty
 * document inside.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class CreateNew extends LocalizableAction {

	private static final long serialVersionUID = -560914862148457240L;
	private JNotepadPP parent;
	private ILocalizationProvider lp;

	/**
	 * Public constructor for this action.
	 * 
	 * @param jNotepadPP
	 *            Reference to JNotepad++ program.
	 */
	public CreateNew(JNotepadPP jNotepadPP, ILocalizationProvider lp) {
		super("New", lp);
		this.lp = lp;
		parent = jNotepadPP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		createNew();

	}

	/**
	 * Method used to create new tab with empty document inside.
	 */
	private void createNew() {
		JTextArea textArea = new JTextArea();
		parent.settingsTA(textArea);

		JScrollPane scrollPane = new JScrollPane(textArea);

		parent.tabbedPane.add(lp.getString("Untitled"), scrollPane);
		parent.textAreas.add(textArea);
		parent.openedFiles.add(null);
		parent.textChanged.add(false);
		parent.tabbedPane.setSelectedComponent(scrollPane);

		if (parent.tabbedPane.getTabCount() > 1) {
			parent.actionMap.get("close").setEnabled(true);
		}

	}

}
