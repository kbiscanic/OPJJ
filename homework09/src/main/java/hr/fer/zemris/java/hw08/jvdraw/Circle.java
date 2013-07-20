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

public class Circle extends GeometricalObject {

	private static int totalNumber = 1;

	private double radius;
	private int number = -1;

	public Circle(Point start, double radius, Color color) {
		super(start, color);
		this.radius = radius;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public void draw(Graphics g, int xOff, int yOff) {
		g.setColor(getColor());

		g.drawOval((int) (getStart().x - radius - xOff), (int) (getStart().y
				- radius - yOff), (int) (2 * radius), (int) (2 * radius));

	}

	@Override
	public String toString() {
		if (number == -1) {
			number = getNumber();
		}
		return "Circle " + number;
	}

	private static int getNumber() {
		return totalNumber++;
	}

	@Override
	public String toJVD() {
		Color color = getColor();
		Point start = getStart();
		return "CIRCLE " + start.x + " " + start.y + " " + radius + " "
				+ color.getRed() + " " + color.getGreen() + " "
				+ color.getBlue();
	}

	@Override
	public boolean showDialog(Component parent) {
		JPanel panel = new JPanel(new GridLayout(2, 2));
		JTextField startX = new JTextField(Integer.toString(getStart().x));
		startX.setBorder(BorderFactory.createTitledBorder("Center x:"));
		startX.setOpaque(false);
		JTextField startY = new JTextField(Integer.toString(getStart().y));
		startY.setBorder(BorderFactory.createTitledBorder("Center y:"));
		startY.setOpaque(false);
		panel.add(startX);
		panel.add(startY);

		JTextField rad = new JTextField(Double.toString(radius));
		rad.setBorder(BorderFactory.createTitledBorder("Radius:"));
		rad.setOpaque(false);
		panel.add(rad);

		JColorArea carea = new JColorArea(null, ColorType.FOREGROUND,
				getColor());
		carea.setBorder(BorderFactory.createTitledBorder("Circle color:"));

		panel.add(carea);

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
					"Radius must be > 0! Canceling changes..", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		setStart(new Point(startx, starty));
		radius = radv;

		setColor(carea.getCurrentColor());

		return true;

	}

	@Override
	public int getTop() {
		return (int) (getStart().y - radius);
	}

	@Override
	public int getLeft() {
		return (int) (getStart().x - radius);
	}

	@Override
	public int getBot() {
		return (int) (getStart().y + radius);
	}

	@Override
	public int getRight() {
		return (int) (getStart().x + radius);
	}

	public static void reset() {
		totalNumber = 1;
	}

}
