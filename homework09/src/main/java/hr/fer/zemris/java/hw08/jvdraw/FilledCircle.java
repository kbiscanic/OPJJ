package hr.fer.zemris.java.hw08.jvdraw;

import hr.fer.zemris.java.hw08.jvdraw.JColorArea.ColorType;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FilledCircle extends Circle {

	private static int totalNumber = 1;

	private Color fillColor;
	private int number = -1;

	public FilledCircle(Point start, double radius, Color color, Color fillColor) {
		super(start, radius, color);
		this.fillColor = fillColor;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	@Override
	public void draw(Graphics g, int xOff, int yOff) {
		double radius = getRadius();
		g.setColor(fillColor);
		g.fillOval((int) (getStart().x - radius - xOff), (int) (getStart().y
				- radius - yOff), (int) (2 * radius), (int) (2 * radius));
		super.draw(g, xOff, yOff);
	}

	@Override
	public String toString() {
		if (number == -1) {
			number = getNumber();
		}
		return "Filled circle " + number;
	}

	private static int getNumber() {
		return totalNumber++;
	}

	@Override
	public String toJVD() {
		Color color = getColor();
		Point start = getStart();
		double radius = getRadius();
		return "FCIRCLE " + start.x + " " + start.y + " " + radius + " "
				+ color.getRed() + " " + color.getGreen() + " "
				+ color.getBlue() + " " + fillColor.getRed() + " "
				+ fillColor.getGreen() + " " + fillColor.getBlue();
	}

	@Override
	public boolean showDialog(Component parent) {
		JPanel panel = new JPanel(new GridLayout(3, 2));
		JTextField startX = new JTextField(Integer.toString(getStart().x));
		startX.setBorder(BorderFactory.createTitledBorder("Center x:"));
		startX.setOpaque(false);
		JTextField startY = new JTextField(Integer.toString(getStart().y));
		startY.setBorder(BorderFactory.createTitledBorder("Center y:"));
		startY.setOpaque(false);
		panel.add(startX);
		panel.add(startY);

		JTextField rad = new JTextField(Double.toString(getRadius()));
		rad.setBorder(BorderFactory.createTitledBorder("Radius:"));
		rad.setOpaque(false);
		panel.add(rad);

		JColorArea carea = new JColorArea(null, ColorType.FOREGROUND,
				getColor());
		carea.setBorder(BorderFactory.createTitledBorder("Circle color:"));

		panel.add(carea);

		JColorArea fcarea = new JColorArea(null, ColorType.BACKGROUND,
				fillColor);
		fcarea.setBorder(BorderFactory.createTitledBorder("Fill color:"));

		panel.add(fcarea);

		int option = JOptionPane.showConfirmDialog(parent, panel, "Properties",
				JOptionPane.OK_CANCEL_OPTION);

		if (option != JOptionPane.OK_OPTION) {
			return false;
		}

		int startx = -1;
		int starty = -1;
		double radv = -1;
		try {
			startx = Integer.parseInt(startX.getText());
			starty = Integer.parseInt(startY.getText());
			radv = Double.parseDouble(rad.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(parent,
					"Invalid format entered. Canceling changes..", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (radv <= 0) {
			JOptionPane.showMessageDialog(parent,
					"Radius must be > 0!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		setStart(new Point(startx, starty));
		setRadius(radv);

		setColor(carea.getCurrentColor());
		fillColor = fcarea.getCurrentColor();

		return true;

	}

	public static void reset() {
		totalNumber = 1;
	}
}
