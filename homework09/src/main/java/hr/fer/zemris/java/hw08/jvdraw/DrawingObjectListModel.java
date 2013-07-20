package hr.fer.zemris.java.hw08.jvdraw;

import javax.swing.AbstractListModel;

public class DrawingObjectListModel extends AbstractListModel<String> implements
		DrawingModelListener {

	private static final long serialVersionUID = -8685101522697988203L;
	private IDrawingModel drawingModel;

	public DrawingObjectListModel(IDrawingModel drawingModel) {
		super();
		this.drawingModel = drawingModel;
		drawingModel.addDrawingModelListener(this);
	}

	@Override
	public void objectsAdded(IDrawingModel source, int index0, int index1) {
		fireIntervalAdded(this, index0, index1);

	}

	@Override
	public void objectsRemoved(IDrawingModel source, int index0, int index1) {
		fireContentsChanged(this, index0, index1);

	}

	@Override
	public void objectsChanged(IDrawingModel source, int index0, int index1) {
		fireContentsChanged(this, index0, index1);

	}

	@Override
	public int getSize() {
		return drawingModel.getSize();
	}

	@Override
	public String getElementAt(int index) {
		return drawingModel.getObject(index).toString();
	}

	public GeometricalObject getObjectAt(int index) {
		return drawingModel.getObject(index);
	}

}
