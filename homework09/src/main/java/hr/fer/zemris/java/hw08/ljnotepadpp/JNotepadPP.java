package hr.fer.zemris.java.hw08.ljnotepadpp;

import hr.fer.zemris.java.hw08.ljnotepadpp.localization.FormLocalizationProvider;
import hr.fer.zemris.java.hw08.ljnotepadpp.localization.ILocalizationListener;
import hr.fer.zemris.java.hw08.ljnotepadpp.localization.LocalizableAction;
import hr.fer.zemris.java.hw08.ljnotepadpp.localization.LocalizationProvider;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Simple text file editor called JNotepad++.
 * 
 * @author Kristijan Biscanic
 * 
 */
public final class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 64578463339743765L;

	/**
	 * Map of actions used by this program.
	 */
	protected Map<String, Action> actionMap = new HashMap<>();
	/**
	 * TabbedPane containing all tabs currently opened.
	 */
	protected JTabbedPane tabbedPane;
	/**
	 * List of {@link File} objects of all currently opened files.
	 */
	protected List<File> openedFiles = new ArrayList<>();
	/**
	 * List of {@link JTextArea} objects of all currently opened files.
	 */
	protected List<JTextArea> textAreas = new ArrayList<>();
	/**
	 * List of booleans used to determine if currently opened file has changed
	 * or not since last saving or opening.
	 */
	protected List<Boolean> textChanged = new ArrayList<>();

	FormLocalizationProvider flp = new FormLocalizationProvider(
			LocalizationProvider.getInstance(), this);

	private static final int MINHEIGHT = 320;

	/**
	 * Private constructor used to create new Frame of this program.
	 */
	private JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(final WindowEvent e) {
				actionMap.get("quit").actionPerformed(null);
			}

		});
		setTitle(flp.getString("Untitled") + " - JNotepad++");
		initGUI();
		pack();
	}

	/**
	 * Method used to initialize Graphic User Interface for JNotepad++.
	 */
	private void initGUI() {
		this.getContentPane().setLayout(new BorderLayout());

		initializeActions();

		tabbedPane = new JTabbedPane();
		tabbedPane.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(final ChangeEvent e) {
				File current;
				if (openedFiles.isEmpty()) {
					current = null;
				} else {
					current = openedFiles.get(tabbedPane.getSelectedIndex());
				}
				String title = null;
				if (current == null) {
					title = flp.getString("Untitled");
				} else {
					title = current.getAbsolutePath();
				}
				JNotepadPP.this.setTitle(title + " - JNotepad++");

			}
		});

		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				File current;
				if (openedFiles.isEmpty()) {
					current = null;
				} else {
					current = openedFiles.get(tabbedPane.getSelectedIndex());
				}
				String title = null;
				if (current == null) {
					title = flp.getString("Untitled");
				} else {
					title = current.getAbsolutePath();
				}
				JNotepadPP.this.setTitle(title + " - JNotepad++");

				int count = JNotepadPP.this.tabbedPane.getComponentCount();

				for (int i = 0; i < count; i++) {
					if (JNotepadPP.this.openedFiles.get(i) != null) {
						continue;
					}
					JNotepadPP.this.tabbedPane.setTitleAt(i,
							flp.getString("Untitled"));
				}

			}
		});
		JToolBar toolbar = new ToolBar(actionMap);

		JMenuBar menu = new MenuBar(actionMap, flp);
		this.setJMenuBar(menu);

		actionMap.get("new").actionPerformed(null);

		this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		this.getContentPane().add(toolbar, BorderLayout.NORTH);
		Dimension minDimension = new Dimension();
		minDimension.width = toolbar.getMinimumSize().width + 10;
		minDimension.height = MINHEIGHT;

		this.setMinimumSize(minDimension);
	}

	/**
	 * Method used to put all actions into a list for easier use.
	 */
	private void initializeActions() {
		Action createNew = new CreateNew(this, flp);

		createNew.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));

		actionMap.put("new", createNew);

		Action open = new Open(this, flp);

		open.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));

		actionMap.put("open", open);

		Action save = new Save(this, flp);

		save.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		actionMap.put("save", save);

		Action saveAs = new SaveAs(this, flp);

		saveAs.putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK
						| InputEvent.SHIFT_DOWN_MASK));
		actionMap.put("saveas", saveAs);

		Action copy = new Copy(this, flp);

		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
		actionMap.put("copy", copy);

		Action paste = new Paste(this, flp);
		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		actionMap.put("paste", paste);

		Action cut = new Cut(this, flp);
		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
		actionMap.put("cut", cut);

		Action stats = new Statistic(this, flp);
		stats.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK));
		actionMap.put("stats", stats);

		Action close = new LocalizableAction("Close", flp) {

			private static final long serialVersionUID = 8950416899424911708L;

			@Override
			public void actionPerformed(ActionEvent e) {
				close(-1, false);

			}
		};
		close.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
		close.setEnabled(false);
		actionMap.put("close", close);

		Action quit = new Quit(this, flp);
		quit.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
		actionMap.put("quit", quit);
	}

	/**
	 * Method used to close the tab at the given position.
	 * 
	 * @param pos
	 *            Determines the position of the tab currently closing.
	 * @param lastTab
	 *            Determines if the tab currently closing is the last tab
	 *            standing.
	 * @return <code>true</code> if the tab is successfully closed;
	 *         <code>false</code> otherwise.
	 */
	protected boolean close(int pos, final boolean lastTab) {
		if (pos == -1) {
			pos = tabbedPane.getSelectedIndex();
		}
		tabbedPane.setSelectedIndex(pos);
		if (textChanged.get(pos)) {

			int choice = JOptionPane.showOptionDialog(this, "Document "
					+ tabbedPane.getTitleAt(pos)
					+ " has changed. Do you want to save it?", "Save "
					+ tabbedPane.getTitleAt(pos) + "?",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, null, null);

			if (choice == JOptionPane.YES_OPTION) {
				actionMap.get("save").actionPerformed(null);
			}

			if (choice == JOptionPane.CANCEL_OPTION) {
				return false;
			}

		}
		if (lastTab) {
			this.dispose();
			return true;
		}

		killtab(pos);

		if (tabbedPane.getTabCount() == 1) {
			actionMap.get("close").setEnabled(false);
		}

		return true;

	}

	/**
	 * Method used to kill the tab at the given position.
	 * 
	 * @param pos
	 *            Position.
	 */
	protected void killtab(final int pos) {
		tabbedPane.remove(pos);

		final int cnt = tabbedPane.getTabCount();

		for (int i = pos; i < cnt; i++) {
			openedFiles.set(i, openedFiles.get(i + 1));
			textAreas.set(i, textAreas.get(i + 1));
			textChanged.set(i, textChanged.get(i + 1));
		}

		openedFiles.remove(cnt);
		textAreas.remove(cnt);
		textChanged.remove(cnt);

	}

	/**
	 * Method used to set all the setting to every textArea created in this
	 * program.
	 * 
	 * @param textArea
	 *            Newly created {@link JTextArea}.
	 */
	protected void settingsTA(final JTextArea textArea) {
		InputMap inputMap = textArea.getInputMap();
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				InputEvent.CTRL_DOWN_MASK), "nothing");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				InputEvent.CTRL_DOWN_MASK), "nothing");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				InputEvent.CTRL_DOWN_MASK), "nothing");

		textArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(final DocumentEvent e) {

				int pos = JNotepadPP.this.tabbedPane.getSelectedIndex();
				boolean curr = JNotepadPP.this.textChanged.get(pos);
				if (curr) {
					return;
				}
				JNotepadPP.this.textChanged.set(pos, true);
				statuschanged(pos);

			}

			@Override
			public void insertUpdate(final DocumentEvent e) {
				int pos = JNotepadPP.this.tabbedPane.getSelectedIndex();
				boolean curr = JNotepadPP.this.textChanged.get(pos);
				if (curr) {
					return;
				}
				JNotepadPP.this.textChanged.set(pos, true);
				statuschanged(pos);

			}

			@Override
			public void changedUpdate(final DocumentEvent e) {
				int pos = JNotepadPP.this.tabbedPane.getSelectedIndex();
				boolean curr = JNotepadPP.this.textChanged.get(pos);
				if (curr) {
					return;
				}
				JNotepadPP.this.textChanged.set(pos, true);
				statuschanged(pos);

			}
		});
	}

	/**
	 * Method that has to be called every time when state of textChanged flag
	 * changes.
	 * 
	 * @param pos
	 *            Position of the flag in the list.
	 */
	protected void statuschanged(final int pos) {
		if (textChanged.get(pos)) {
			tabbedPane.setTitleAt(pos, "*" + tabbedPane.getTitleAt(pos));
		} else {
			tabbedPane.setTitleAt(pos, openedFiles.get(pos).getName());
		}

	}

	/**
	 * Main method and entry point for this program.
	 * 
	 * @param args
	 *            Not used.
	 */
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new JNotepadPP().setVisible(true);

			}
		});

	}

}
