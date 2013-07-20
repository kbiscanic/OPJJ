package hr.fer.zemris.java.hw07.layoutmans;

import hr.fer.zemris.java.hw07.layoutmans.StackedLayout.StackedLayoutDirection;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.LayoutManager2;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program used to demonstrate usage of {@link StackedLayout} layout manager as
 * well as its all 3 enum options.
 * 
 * @author Kristijan Biscanic
 * 
 */
public final class ExampleStackedFrame extends JFrame {

	private static final long serialVersionUID = 4785118404968015070L;

	/**
	 * Private constructor used to create new Frame.
	 */
	private ExampleStackedFrame() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Primjer uporabe StackedLayouta");
		initGUI();
		pack();
	}

	/**
	 * Method used to initialize Graphic User Interface.
	 */
	private void initGUI() {
		this.getContentPane().setLayout(new GridLayout(1, 3));
		this.getContentPane().add(
				makePanel("Odozgo", new StackedLayout(
						StackedLayoutDirection.FROM_TOP)));
		this.getContentPane().add(
				makePanel("Odozdo", new StackedLayout(
						StackedLayoutDirection.FROM_BOTTOM)));
		this.getContentPane().add(
				makePanel("Ispuna", new StackedLayout(
						StackedLayoutDirection.FILL)));
	}

	/**
	 * Method used to create a {@link JPanel} that we will insert into out
	 * {@link StackedLayout}.
	 * 
	 * @param text
	 *            Text.
	 * @param manager
	 *            Layout Manager used to manage this panel.
	 * @return Newly created panel.
	 */
	private Component makePanel(String text, LayoutManager2 manager) {
		JPanel panel = new JPanel(manager);
		panel.setBorder(BorderFactory.createTitledBorder(text));

		JPanel p1 = new JPanel(new GridLayout(3, 1));
		p1.setBorder(BorderFactory.createTitledBorder("Komponenta 1"));
		p1.add(new JButton("Gumb 1"));
		p1.add(new JButton("Gumb 2"));
		p1.add(new JButton("Gumb 3"));

		JPanel p2 = new JPanel(new GridLayout(2, 1));
		p2.setBorder(BorderFactory.createTitledBorder("Komponenta 2"));
		p2.add(new JLabel("Prva od dvije labele"));
		p2.add(new JLabel("Druga od dvije labele"));

		panel.add(p1);
		panel.add(p2);
		panel.add(new JLabel("Izolirana labela"));

		return panel;
	}

	/**
	 * Main method for this program.
	 * 
	 * @param args
	 *            Unused.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new ExampleStackedFrame().setVisible(true);
			}
		});

	}

}
