package hr.fer.zemris.java.hw07.jnotepadpp;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Action associated with New buttons. This action creates new tab with empty
 * document inside.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class CreateNew extends AbstractAction {

	private static final long serialVersionUID = -560914862148457240L;
	private JNotepadPP parent;

	/**
	 * Public constructor for this action.
	 * 
	 * @param jNotepadPP
	 *            Reference to JNotepad++ program.
	 */
	public CreateNew(JNotepadPP jNotepadPP) {
		super("New");
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

		parent.tabbedPane.add("Untitled document", scrollPane);
		parent.textAreas.add(textArea);
		parent.openedFiles.add(null);
		parent.textChanged.add(false);
		parent.tabbedPane.setSelectedComponent(scrollPane);

		if (parent.tabbedPane.getTabCount() > 1) {
			parent.actionMap.get("close").setEnabled(true);
		}

	}

}
