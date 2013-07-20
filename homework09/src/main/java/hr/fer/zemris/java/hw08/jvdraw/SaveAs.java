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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class SaveAs extends AbstractAction {

	private static final long serialVersionUID = -7010084727403729227L;
	private static JFrame parent;
	private static DrawingModel drawingModel;

	public SaveAs(JFrame parent, DrawingModel drawingModel) {
		super("Save As...");
		setParent(parent);
		setDrawingModel(drawingModel);
		this.putValue(
				Action.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK
						| InputEvent.SHIFT_DOWN_MASK));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		saveFileAs();
	}

	private static void setParent(JFrame parent) {
		SaveAs.parent = parent;
	}

	private static void setDrawingModel(DrawingModel drawingModel) {
		SaveAs.drawingModel = drawingModel;
	}

	protected static boolean saveFileAs() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(JvdExtensionFilter.getInstance());
		fc.setAcceptAllFileFilterUsed(false);

		int returnValue = fc.showSaveDialog(parent);

		if (returnValue != JFileChooser.APPROVE_OPTION) {
			return false;
		}

		File file = fc.getSelectedFile();

		if (!file.getAbsolutePath().endsWith(".jvd")) {
			file = new File(file.getAbsoluteFile() + ".jvd");
		}

		if (file.exists()) {
			int choice = JOptionPane.showOptionDialog(parent,
					"File " + file.getName()
							+ " already exists. Do you want to replace it?",
					"Replace " + file.getName() + "?",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
					null, null, null);
			if (choice != JOptionPane.YES_OPTION) {
				return false;
			}
		}

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
		drawingModel.openedFile = file;
		return true;

	}

}
