package hr.fer.zemris.java.hw08.jvdraw;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class Exit extends AbstractAction {

	private static final long serialVersionUID = -2772643416825650241L;
	private static JFrame parent;
	private static DrawingModel drawingModel;

	public Exit(JFrame parent, DrawingModel drawingModel) {
		super("Exit");
		setParent(parent);
		setDrawingModel(drawingModel);
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
	}

	private static void setParent(JFrame parent) {
		Exit.parent = parent;
	}

	private static void setDrawingModel(DrawingModel drawingModel) {
		Exit.drawingModel = drawingModel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		exit();
	}

	protected static void exit() {
		if (drawingModel.changed) {
			int choice = JOptionPane.showOptionDialog(parent,
					"File has changed. Do you want to save it?",
					"Save changes?", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (choice == JOptionPane.CANCEL_OPTION) {
				return;
			}
			if (choice == JOptionPane.YES_OPTION) {
				if (!Save.saveFile()) {
					return;
				}
			}
		}

		parent.dispose();
	}

}
