package hr.fer.zemris.java.hw08.jvdraw;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;

public abstract class GeometricalObject {

	private Point start;
	private Color color;

	public GeometricalObject(Point start, Color color) {
		super();
		this.start = start;
		this.color = color;
	}

	public Point getStart() {
		return start;
	}

	public void setStart(Point start) {
		this.start = start;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public abstract void draw(Graphics g, int xOff, int yOff);

	public abstract String toJVD();

	public abstract boolean showDialog(Component parent);

	public abstract int getTop();

	public abstract int getLeft();

	public abstract int getBot();

	public abstract int getRight();

}
