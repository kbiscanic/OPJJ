package hr.fer.zemris.java.hw08.jvdraw;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DrawingModel implements IDrawingModel {

	private List<DrawingModelListener> listeners = new ArrayList<>();
	private List<GeometricalObject> objects = new ArrayList<>();
	protected boolean changed = false;
	protected File openedFile = null;

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		int index = objects.size();
		objects.add(object);

		for (DrawingModelListener l : listeners) {
			l.objectsAdded(this, index, index);
		}

		if (!changed) {
			changed = true;
		}
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	public void clear() {
		int end = objects.size();
		objects = new ArrayList<>();

		Circle.reset();
		Line.reset();
		FilledCircle.reset();

		for (DrawingModelListener l : listeners) {
			l.objectsRemoved(this, 0, end);
		}
	}

	public void objectChangedAt(int index) {
		for (DrawingModelListener l : listeners) {
			l.objectsAdded(this, index, index);
		}

		if (!changed) {
			changed = true;
		}
	}

}
