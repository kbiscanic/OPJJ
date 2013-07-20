package hr.fer.zemris.java.hw08.ljnotepadpp;

import java.util.Map;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 * Class used to construct a toolbar for JNotepad++ with desired layout of
 * buttons.
 * 
 * @author Kristijan Biscanic
 * 
 */
public class ToolBar extends JToolBar {

	/**
	 * Public constructor used to construct the toolbar.
	 * 
	 * @param actionMap
	 *            Map of actions used from this toolbar.
	 */
	public ToolBar(Map<String, Action> actionMap) {
		this.add(new JButton(actionMap.get("new")));
		this.add(new JButton(actionMap.get("open")));
		this.add(new JButton(actionMap.get("save")));
		this.add(new JButton(actionMap.get("saveas")));
		this.add(new JToolBar.Separator());
		this.add(new JButton(actionMap.get("cut")));
		this.add(new JButton(actionMap.get("copy")));
		this.add(new JButton(actionMap.get("paste")));
		this.add(new JToolBar.Separator());
		this.add(new JButton(actionMap.get("stats")));
		this.add(new JToolBar.Separator());
		this.add(new JButton(actionMap.get("close")));
		this.add(new JButton(actionMap.get("quit")));
	}

	private static final long serialVersionUID = 8161211399153858064L;

}
