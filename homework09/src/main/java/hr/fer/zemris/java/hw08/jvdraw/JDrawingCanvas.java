package hr.fer.zemris.java.hw08.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;
import javax.swing.JToggleButton;

public class JDrawingCanvas extends JComponent implements DrawingModelListener,
		ColorChangeListener {

	private static final long serialVersionUID = -971274016202737387L;
	private final IColorProvider foreground;
	private final IColorProvider background;
	private Color fClr;
	private Color bClr;

	private Point start = null;
	private Point end = null;

	private final JToggleButton line;
	private final JToggleButton circle;
	private final JToggleButton fcircle;

	private IDrawingModel drawingModel;

	public JDrawingCanvas(IColorProvider foreground, IColorProvider background,
			final JToggleButton line, final JToggleButton circle,
			final JToggleButton ficircle, IDrawingModel drawingModel) {
		super();
		this.foreground = foreground;
		this.background = background;
		this.fClr = foreground.getCurrentColor();
		this.bClr = background.getCurrentColor();
		this.line = line;
		this.circle = circle;
		this.fcircle = ficircle;
		this.drawingModel = drawingModel;
		foreground.addColorChangeListener(this);
		background.addColorChangeListener(this);
		drawingModel.addDrawingModelListener(this);

		setMinimumSize(new Dimension(200, 200));
		setPreferredSize(new Dimension(200, 200));

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (start == null) {
					start = e.getPoint();
					return;
				}

				end = e.getPoint();

				if (line.isSelected()) {
					JDrawingCanvas.this.drawingModel.add(new Line(start, end,
							fClr));
				} else if (circle.isSelected()) {
					JDrawingCanvas.this.drawingModel.add(new Circle(start,
							start.distance(end), fClr));
				} else if (fcircle.isSelected()) {
					JDrawingCanvas.this.drawingModel.add(new FilledCircle(
							start, start.distance(end), fClr, bClr));
				}

				start = null;
				end = null;

			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (start != null) {
					end = e.getPoint();
					repaint();
				}

			}
		});
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor,
			Color newColor) {
		if (source == foreground) {
			fClr = newColor;
			return;
		} else if (source == background) {
			bClr = newColor;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		Insets ins = getInsets();
		Dimension dim = getSize();
		g.setColor(Color.WHITE);
		g.fillRect(ins.left, ins.top, dim.width - ins.left - ins.right,
				dim.height - ins.top - ins.bottom);
		int cnt = drawingModel.getSize();
		for (int i = 0; i < cnt; i++) {
			drawingModel.getObject(i).draw(g, 0, 0);
		}
		try {
			if (start != null) {
				g.setColor(fClr);
				if (line.isSelected()) {
					new Line(start, end, fClr).draw(g, 0, 0);
				} else if (circle.isSelected()) {
					new Circle(start, start.distance(end), fClr).draw(g, 0, 0);
				} else {
					new FilledCircle(start, start.distance(end), fClr, bClr)
							.draw(g, 0, 0);
				}
			}
		} catch (NullPointerException ignoreable) {
		}

	}

	@Override
	public void objectsAdded(IDrawingModel source, int index0, int index1) {
		repaint();

	}

	@Override
	public void objectsRemoved(IDrawingModel source, int index0, int index1) {
		repaint();

	}

	@Override
	public void objectsChanged(IDrawingModel source, int index0, int index1) {
		repaint();
	}

}
