package hr.fer.zemris.java.hw08.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class JColorArea extends JComponent implements IColorProvider {

	private static final long serialVersionUID = -7939804816321764080L;
	private Color selectedColor;
	private List<ColorChangeListener> listeners = new ArrayList<>();

	public JColorArea(final JFrame parent, final ColorType type,
			Color selectedColor) {
		super();
		this.selectedColor = selectedColor;
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Color oldColor = JColorArea.this.selectedColor;

				Color newColor = JColorChooser
						.showDialog(
								parent,
								type == ColorType.FOREGROUND ? "Choose new foreground color"
										: "Choose new background color",
								oldColor);
				if (newColor == null || newColor == oldColor) {
					return;
				}
				JColorArea.this.selectedColor = newColor;

				repaint();

				colorChanged(oldColor, newColor);
			}
		});
	}

	protected void colorChanged(Color oldColor, Color newColor) {
		for (ColorChangeListener l : listeners) {
			l.newColorSelected(this, oldColor, newColor);
		}

	}

	@Override
	@Transient
	public Dimension getPreferredSize() {
		Insets ins = getInsets();
		return new Dimension(ins.left + ins.right + 15, ins.top + ins.bottom
				+ 15);
	}

	@Override
	@Transient
	public Dimension getMinimumSize() {
		Insets ins = getInsets();
		return new Dimension(ins.left + ins.right + 15, ins.top + ins.bottom
				+ 15);
	}

	@Override
	@Transient
	public Dimension getMaximumSize() {
		Insets ins = getInsets();
		return new Dimension(ins.left + ins.right + 15, ins.top + ins.bottom
				+ 15);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(selectedColor);
		Dimension dimension = getSize();
		Insets insets = getInsets();
		g.fillRect(insets.left, insets.top, dimension.width - insets.right
				- insets.left, dimension.height - insets.top - insets.bottom);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}

	public enum ColorType {
		FOREGROUND, BACKGROUND;
	}

}
