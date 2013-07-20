package hr.fer.zemris.java.hw07.jnotepadpp;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 * Action associated with Statistic buttons. This action displays new
 * {@link JDialog} with statistical information on the current file.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class Statistic extends AbstractAction {

	private static final long serialVersionUID = 6676273823140947205L;
	private JNotepadPP parent;

	/**
	 * Public constructor for this action.
	 * 
	 * @param jNotepadPP
	 *            Reference to JNotepad++ program.
	 */
	public Statistic(JNotepadPP jNotepadPP) {
		super("Statistic");
		parent = jNotepadPP;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		stats();

	}

	/**
	 * Method used to calculate and display statistical information.
	 */
	private void stats() {
		String text = parent.textAreas
				.get(parent.tabbedPane.getSelectedIndex()).getText();
		int chars = text.length();
		int whitespaces = 0;
		int lines = 1;
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (ch == '\t' || ch == ' ') {
				whitespaces++;
			}
			if (ch == '\n') {
				whitespaces++;
				lines++;
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Your document contains " + chars + " characters, "
				+ (chars - whitespaces) + " non-whitespace characters and "
				+ lines + " lines." + "\n");
		JOptionPane.showMessageDialog(parent, sb.toString(),
				"Statistical information", JOptionPane.INFORMATION_MESSAGE);

	}

}
