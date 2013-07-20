package hr.fer.zemris.java.hw08.jvdraw;

import hr.fer.zemris.java.hw08.jvdraw.JColorArea.ColorType;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

public final class JVDraw extends JFrame {

	private static final long serialVersionUID = 4560180454895132385L;
	private static ButtonGroup group;
	private static JToggleButton line = new JToggleButton("Line");
	private static JToggleButton circle = new JToggleButton("Circle");
	private static JToggleButton fcircle = new JToggleButton("Filled circle");
	JSplitPane splitted;

	private JVDraw() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				Exit.exit();
			}

		});
		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				splitted.setDividerLocation(0.85);
			}

		});
		setTitle("JVDraw");
		setGroup(new ButtonGroup());
		initGUI();
		pack();
	}

	private static void setGroup(ButtonGroup group) {
		JVDraw.group = group;
	}

	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());

		JToolBar toolbar = new JToolBar();
		JColorArea foreground = new JColorArea(this, ColorType.FOREGROUND,
				Color.BLUE);
		foreground.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		JColorArea background = new JColorArea(this, ColorType.BACKGROUND,
				Color.YELLOW);
		background.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		toolbar.add(foreground);
		toolbar.addSeparator();
		toolbar.add(background);
		toolbar.addSeparator();

		line.setSelected(true);
		group.add(line);
		group.add(circle);
		group.add(fcircle);

		toolbar.add(line);
		toolbar.add(circle);
		toolbar.add(fcircle);

		getContentPane().add(toolbar, BorderLayout.PAGE_START);

		getContentPane().add(new JColorDisplay(foreground, background),
				BorderLayout.PAGE_END);

		DrawingModel drawingModel = new DrawingModel();
		splitted = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitted.add(new JScrollPane(new JDrawingCanvas(foreground, background,
				line, circle, fcircle, drawingModel)));
		splitted.add(new JScrollPane(new JGeometricalObjectList(this,
				drawingModel, new DrawingObjectListModel(drawingModel))));
		splitted.setSize(getPreferredSize());
		splitted.setDividerLocation(0.85);

		getContentPane().add(splitted, BorderLayout.CENTER);

		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		file.add(new JMenuItem(new Open(this, drawingModel)));
		file.add(new JMenuItem(new Save(this, drawingModel)));
		file.add(new JMenuItem(new SaveAs(this, drawingModel)));
		file.add(new JSeparator());
		file.add(new JMenuItem(new Export(this, drawingModel)));
		file.add(new JSeparator());
		file.add(new JMenuItem(new Exit(this, drawingModel)));
		menubar.add(file);

		setJMenuBar(menubar);

	}

	public static void main(String[] args) throws InterruptedException {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				new JVDraw().setVisible(true);

			}
		});

	}

}
