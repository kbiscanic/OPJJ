package hr.fer.zemris.java.hw08.jvdraw;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class Open extends AbstractAction {

	private static final long serialVersionUID = -5098568094553772641L;
	private JFrame parent;
	private DrawingModel drawingModel;

	public Open(JFrame parent, DrawingModel drawingModel) {
		super("Open...");
		this.parent = parent;
		this.drawingModel = drawingModel;
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		openFile();
	}

	private void openFile() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setFileFilter(JvdExtensionFilter.getInstance());
		fc.setAcceptAllFileFilterUsed(false);

		int returnValue = fc.showOpenDialog(parent);

		if (returnValue != JFileChooser.APPROVE_OPTION) {
			return;
		}

		drawingModel.clear();

		File file = fc.getSelectedFile();

		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file), StandardCharsets.UTF_8))) {
			String line = br.readLine();

			while (line != null) {
				line = line.trim();
				if (line.isEmpty()) {
					break;
				}

				if (line.matches("LINE .*")) {
					String[] splitted = line.split("\\s");
					if (splitted.length != 8) {
						continue;
					}
					Point start = new Point();
					start.x = Integer.parseInt(splitted[1]);
					start.y = Integer.parseInt(splitted[2]);

					Point end = new Point();
					end.x = Integer.parseInt(splitted[3]);
					end.y = Integer.parseInt(splitted[4]);

					Color color = new Color(Integer.parseInt(splitted[5]),
							Integer.parseInt(splitted[6]),
							Integer.parseInt(splitted[7]));

					drawingModel.add(new Line(start, end, color));
				} else if (line.matches("CIRCLE .*")) {
					String[] splitted = line.split("\\s");
					if (splitted.length != 7) {
						continue;
					}
					Point start = new Point();
					start.x = Integer.parseInt(splitted[1]);
					start.y = Integer.parseInt(splitted[2]);

					double radius = Double.parseDouble(splitted[3]);

					Color color = new Color(Integer.parseInt(splitted[4]),
							Integer.parseInt(splitted[5]),
							Integer.parseInt(splitted[6]));

					drawingModel.add(new Circle(start, radius, color));

				} else if (line.matches("FCIRCLE .*")) {
					String[] splitted = line.split("\\s");
					if (splitted.length != 10) {
						continue;
					}
					Point start = new Point();
					start.x = Integer.parseInt(splitted[1]);
					start.y = Integer.parseInt(splitted[2]);
					double radius = Double.parseDouble(splitted[3]);

					Color color = new Color(Integer.parseInt(splitted[4]),
							Integer.parseInt(splitted[5]),
							Integer.parseInt(splitted[6]));

					Color bcolor = new Color(Integer.parseInt(splitted[7]),
							Integer.parseInt(splitted[8]),
							Integer.parseInt(splitted[9]));
					drawingModel.add(new FilledCircle(start, radius, color,
							bcolor));
				}

				line = br.readLine();

			}

		} catch (IOException | NumberFormatException e) {
			JOptionPane
					.showMessageDialog(
							parent,
							"Error opening file! File may be missing or incorrectly formatted!",
							"Error!", JOptionPane.ERROR_MESSAGE);
			return;
		}

		drawingModel.changed = false;
		drawingModel.openedFile = file;

	}
}
