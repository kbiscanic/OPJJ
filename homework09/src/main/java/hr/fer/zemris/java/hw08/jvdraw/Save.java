package hr.fer.zemris.java.hw08.jvdraw;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class Save extends AbstractAction {

	private static final long serialVersionUID = 5971111390552124773L;
	private static JFrame parent;
	private static DrawingModel drawingModel;

	public Save(JFrame parent, DrawingModel drawingModel) {
		super("Save");
		setParent(parent);
		setDrawingModel(drawingModel);
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		saveFile();
	}

	private static void setParent(JFrame parent) {
		Save.parent = parent;
	}

	private static void setDrawingModel(DrawingModel drawingModel) {
		Save.drawingModel = drawingModel;
	}

	protected static boolean saveFile() {
		if (drawingModel.openedFile == null) {

			return SaveAs.saveFileAs();
		}

		File file = drawingModel.openedFile;

		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
			int count = drawingModel.getSize();
			for (int i = 0; i < count; i++) {
				bw.write(drawingModel.getObject(i).toJVD());
				bw.newLine();
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(parent, "Error saving file!",
					"Error!", JOptionPane.ERROR_MESSAGE);
		}

		drawingModel.changed = false;

		return true;
	}

}
