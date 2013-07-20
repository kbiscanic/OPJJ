package hr.fer.zemris.java.hw08.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;

public class JColorDisplay extends JLabel implements ColorChangeListener {

	private static final long serialVersionUID = 4674148173826937218L;
	private IColorProvider foreground;
	private IColorProvider background;
	private int[][] currentColors = new int[2][3];

	public JColorDisplay(IColorProvider foreground, IColorProvider background) {
		super();
		this.foreground = foreground;
		this.background = background;

		extractColors(0);
		extractColors(1);

		setLabel();

		foreground.addColorChangeListener(this);
		background.addColorChangeListener(this);

	}

	private void setLabel() {
		setText("Foreground color: (" + currentColors[0][0] + ", "
				+ currentColors[0][1] + ", " + currentColors[0][2]
				+ "), background color: (" + currentColors[1][0] + ", "
				+ currentColors[1][1] + ", " + currentColors[1][2] + ").");

	}

	private void extractColors(int i) {
		IColorProvider cp = background;
		if (i == 0) {
			cp = foreground;
		}

		currentColors[i][0] = cp.getCurrentColor().getRed();
		currentColors[i][1] = cp.getCurrentColor().getGreen();
		currentColors[i][2] = cp.getCurrentColor().getBlue();

	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor,
			Color newColor) {
		if (source == foreground) {
			extractColors(0);
		} else {
			extractColors(1);
		}

		setLabel();

	}

}
