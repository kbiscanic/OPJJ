package hr.fer.zemris.java.hw08.jvdraw;

public interface DrawingModelListener {

	void objectsAdded(IDrawingModel source, int index0, int index1);

	void objectsRemoved(IDrawingModel source, int index0, int index1);

	void objectsChanged(IDrawingModel source, int index0, int index1);

}
