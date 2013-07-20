package hr.fer.zemris.java.hw08.ljnotepadpp;

import hr.fer.zemris.java.hw08.ljnotepadpp.localization.ILocalizationProvider;
import hr.fer.zemris.java.hw08.ljnotepadpp.localization.LJMenu;
import hr.fer.zemris.java.hw08.ljnotepadpp.localization.Switch;

import java.util.Map;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

/**
 * Class used to construct a menu bar for JNotepad++ with desired layout of
 * menus.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = -1487115244858820398L;

	/**
	 * Public constructor used to construct the menu bar.
	 * 
	 * @param actionMap
	 *            Map of actions used from this menu.
	 */
	public MenuBar(Map<String, Action> actionMap, ILocalizationProvider lp) {
		JMenu file = new LJMenu("File", lp);
		this.add(file);
		file.add(new JMenuItem(actionMap.get("new")));
		file.add(new JMenuItem(actionMap.get("open")));
		file.add(new JSeparator());
		file.add(new JMenuItem(actionMap.get("save")));
		file.add(new JMenuItem(actionMap.get("saveas")));
		file.add(new JSeparator());
		file.add(new JMenuItem(actionMap.get("stats")));
		file.add(new JSeparator());
		file.add(new JMenuItem(actionMap.get("close")));
		file.add(new JMenuItem(actionMap.get("quit")));

		JMenu edit = new LJMenu("Edit", lp);
		this.add(edit);
		edit.add(new JMenuItem(actionMap.get("cut")));
		edit.add(new JMenuItem(actionMap.get("copy")));
		edit.add(new JMenuItem(actionMap.get("paste")));

		JMenu lang = new LJMenu("Languages", lp);
		this.add(lang);
		lang.add(new JMenuItem(new Switch("English", lp)));
		lang.add(new JMenuItem(new Switch("Croatian", lp)));
	}

}
