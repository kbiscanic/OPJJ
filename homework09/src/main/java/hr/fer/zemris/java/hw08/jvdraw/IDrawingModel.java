package hr.fer.zemris.java.hw08.jvdraw;

public interface IDrawingModel {

	int getSize();

	GeometricalObject getObject(int index);

	void add(GeometricalObject object);

	void addDrawingModelListener(DrawingModelListener l);

	void removeDrawingModelListener(DrawingModelListener l);

}
