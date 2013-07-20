package hr.fer.zemris.java.hw08.jvdraw;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListModel;

public class JGeometricalObjectList extends JList<String> {

	private static final long serialVersionUID = 7452002565569466481L;

	public JGeometricalObjectList(final JFrame parent,
			final DrawingModel model, final ListModel<String> dataModel) {
		super(dataModel);

		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() != 2) {
					return;
				}
				int index = JGeometricalObjectList.this.getSelectedIndex();
				if (((DrawingObjectListModel) dataModel).getObjectAt(index)
						.showDialog(parent)) {
					model.objectChangedAt(index);
				}
			}

		});
	}

}
