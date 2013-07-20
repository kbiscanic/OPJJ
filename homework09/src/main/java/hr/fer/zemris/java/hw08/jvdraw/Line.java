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

public class Line extends GeometricalObject {

	private static int totalNumber = 1;

	private Point end;
	private int number = -1;

	public Line(Point start, Point end, Color color) {
		super(start, color);
		this.end = end;
	}

	public Point getEnd() {
		return end;
	}

	public void setEnd(Point end) {
		this.end = end;
	}

	@Override
	public void draw(Graphics g, int xOff, int yOff) {
		g.setColor(getColor());

		g.drawLine(getStart().x - xOff, getStart().y - yOff, end.x - xOff,
				end.y - yOff);

	}

	@Override
	public String toString() {
		if (number == -1) {
			number = getNumber();
		}
		return "Line " + number;
	}
	
	private static int getNumber() {
		return totalNumber++;
	}

	@Override
	public String toJVD() {
		Color color = getColor();
		Point start = getStart();
		return "LINE " + start.x + " " + start.y + " " + end.x + " " + end.y
				+ " " + color.getRed() + " " + color.getGreen() + " "
				+ color.getBlue();
	}

	@Override
	public boolean showDialog(Component parent) {
		JPanel panel = new JPanel(new GridLayout(3, 2));
		JTextField startX = new JTextField(Integer.toString(getStart().x));
		startX.setBorder(BorderFactory.createTitledBorder("Start x:"));
		startX.setOpaque(false);
		JTextField startY = new JTextField(Integer.toString(getStart().y));
		startY.setBorder(BorderFactory.createTitledBorder("Start y:"));
		startY.setOpaque(false);
		panel.add(startX);
		panel.add(startY);

		JTextField endX = new JTextField(Integer.toString(end.x));
		endX.setBorder(BorderFactory.createTitledBorder("End x:"));
		endX.setOpaque(false);
		JTextField endY = new JTextField(Integer.toString(end.y));
		endY.setBorder(BorderFactory.createTitledBorder("End y:"));
		endY.setOpaque(false);
		panel.add(endX);
		panel.add(endY);

		JColorArea carea = new JColorArea(null, ColorType.FOREGROUND,
				getColor());
		carea.setBorder(BorderFactory.createTitledBorder("Line color:"));

		panel.add(carea);

		int option = JOptionPane.showConfirmDialog(parent, panel, "Properties",
				JOptionPane.OK_CANCEL_OPTION);

		if (option != JOptionPane.OK_OPTION) {
			return false;
		}

		int startx = -1;
		int starty = -1;
		int endx = -1;
		int endy = -1;
		try {
			startx = Integer.parseInt(startX.getText());
			starty = Integer.parseInt(startY.getText());
			endx = Integer.parseInt(endX.getText());
			endy = Integer.parseInt(endY.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(parent,
					"Invalid format entered. Canceling changes..", "Error",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		setStart(new Point(startx, starty));
		end.x = endx;
		end.y = endy;

		setColor(carea.getCurrentColor());

		return true;
	}

	@Override
	public int getTop() {
		return Math.min(getStart().y, end.y);
	}

	@Override
	public int getLeft() {
		return Math.min(getStart().x, end.x);
	}

	@Override
	public int getBot() {
		return Math.max(getStart().y, end.y);
	}

	@Override
	public int getRight() {
		return Math.max(getStart().x, end.x);
	}

	public static void reset() {
		totalNumber = 1;
	}
}
