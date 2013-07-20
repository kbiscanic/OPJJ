package hr.fer.zemris.java.hw08.jvdraw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Export extends AbstractAction {

	private static final long serialVersionUID = -5098568094553772641L;
	private JFrame parent;
	private DrawingModel drawingModel;

	public Export(JFrame parent, DrawingModel drawingModel) {
		super("Export to...");
		this.parent = parent;
		this.drawingModel = drawingModel;
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		openFile();
	}

	private void openFile() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter jpg = new FileNameExtensionFilter("JPEG file",
				"jpg", "jpeg");
		fc.setFileFilter(jpg);
		FileNameExtensionFilter png = new FileNameExtensionFilter("PNG file",
				"png");
		fc.addChoosableFileFilter(png);
		FileNameExtensionFilter gif = new FileNameExtensionFilter("GIF file",
				"gif");
		fc.addChoosableFileFilter(gif);
		fc.setAcceptAllFileFilterUsed(false);

		int returnValue = fc.showSaveDialog(parent);

		if (returnValue != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File file = fc.getSelectedFile();
		FileNameExtensionFilter selected = (FileNameExtensionFilter) fc
				.getFileFilter();
		if (selected == jpg) {
			if (file.getAbsolutePath().endsWith(".jpg")
					|| file.getAbsolutePath().endsWith(".jpeg")) {
			} else {
				file = new File(file.getAbsoluteFile() + ".jpg");
			}
		} else if (selected == png) {
			if (!file.getAbsolutePath().endsWith(".png")) {
				file = new File(file.getAbsoluteFile() + ".png");
			}
		} else {
			if (!file.getAbsolutePath().endsWith(".gif")) {
				file = new File(file.getAbsoluteFile() + ".gif");
			}
		}

		if (file.exists()) {
			int choice = JOptionPane.showOptionDialog(parent,
					"File " + file.getName()
							+ " already exists. Do you want to replace it?",
					"Replace " + file.getName() + "?",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
					null, null, null);
			if (choice != JOptionPane.YES_OPTION) {
				return;
			}
		}

		int cnt = drawingModel.getSize();
		if (cnt == 0) {
			JOptionPane
					.showMessageDialog(
							parent,
							"Nothing to be exported. There are no objects in the image.",
							"Error!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		GeometricalObject temp = drawingModel.getObject(0);
		int top = temp.getTop();
		int bot = temp.getBot();
		int left = temp.getLeft();
		int right = temp.getRight();
		for (int i = 1; i < cnt; i++) {
			temp = drawingModel.getObject(i);
			top = Math.min(top, temp.getTop());
			bot = Math.max(bot, temp.getBot());
			left = Math.min(left, temp.getLeft());
			right = Math.max(right, temp.getRight());
		}

		BufferedImage image = new BufferedImage(right - left + 1,
				bot - top + 1, BufferedImage.TYPE_3BYTE_BGR);

		Graphics2D g = image.createGraphics();

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, right - left + 1, bot - top + 1);

		for (int i = 0; i < cnt; i++) {
			drawingModel.getObject(i).draw(g, left, top);
		}
		g.dispose();

		try {
			if (file.getAbsolutePath().endsWith("png")) {
				ImageIO.write(image, "png", file);
			} else if (file.getAbsolutePath().endsWith("gif")) {
				ImageIO.write(image, "gif", file);
			} else {
				ImageIO.write(image, "jpg", file);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(parent, "Error exporting image!",
					"Error!", JOptionPane.ERROR_MESSAGE);
		}

		JOptionPane.showMessageDialog(parent, file.getName()
				+ " exported succesfully!", "Export done!",
				JOptionPane.PLAIN_MESSAGE);

	}
}
