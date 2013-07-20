package hr.fer.zemris.java.tecaj;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class Program3 extends JFrame {

	private JTextField broj;
	private Map<String, Action> mapaAkcija = new HashMap<>();

	public Program3() {
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Kalkulator!");
		this.setLocation(10, 10);
		this.setSize(500, 200);

		initGUI();

		pack();
	}

	private void initGUI() {
		this.getContentPane().setLayout(new BorderLayout());

		inicijalizirajMapuAkcija();

		broj = new JTextField("0");

		this.getContentPane().add(broj, BorderLayout.PAGE_START);

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Operacije"));
		panel.setLayout(new GridLayout(2, 3, 4, 2));
		this.getContentPane().add(panel, BorderLayout.CENTER);

		final JButton gumb1 = new JButton(mapaAkcija.get("sin"));
		JButton gumb2 = new JButton(mapaAkcija.get("cos"));
		JButton gumb3 = new JButton(mapaAkcija.get("tan"));
		JButton gumb4 = new JButton(mapaAkcija.get("e^x"));
		JButton gumb5 = new JButton(mapaAkcija.get("log"));
		JButton gumb6 = new JButton(mapaAkcija.get("ln"));

		panel.add(gumb1);
		panel.add(gumb2);
		panel.add(gumb3);
		panel.add(gumb4);
		panel.add(gumb5);
		panel.add(gumb6);

		JMenuBar traka = new JMenuBar();
		this.setJMenuBar(traka);

		JMenu operacije = new JMenu("Operacije");
		traka.add(operacije);

		JMenuItem stavka1 = new JMenuItem(mapaAkcija.get("sin"));
		JMenuItem stavka2 = new JMenuItem(mapaAkcija.get("cos"));
		JMenuItem stavka3 = new JMenuItem(mapaAkcija.get("tan"));
		JMenuItem stavka4 = new JMenuItem(mapaAkcija.get("e^x"));
		JMenuItem stavka5 = new JMenuItem(mapaAkcija.get("log"));
		JMenuItem stavka6 = new JMenuItem(mapaAkcija.get("ln"));

		operacije.add(stavka1);
		operacije.add(stavka2);
		operacije.add(stavka3);
		operacije.add(stavka4);
		operacije.add(stavka5);
		operacije.add(stavka6);

	}

	private void inicijalizirajMapuAkcija() {
		Action akcija = new AbstractAction("sin(x)") {

			@Override
			public void actionPerformed(ActionEvent e) {
				izracunaj(new ITransformacija() {

					@Override
					public double transformiraj(double x) {
						return Math.sin(x);
					}
				});
			}
		};

		akcija.putValue(Action.SHORT_DESCRIPTION, "Računa sinus broja.");
		akcija.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));

		mapaAkcija.put("sin", akcija);

		akcija = new AbstractAction("cos(x)") {

			@Override
			public void actionPerformed(ActionEvent e) {
				izracunaj(new ITransformacija() {

					@Override
					public double transformiraj(double x) {
						return Math.cos(x);
					}
				});
			}
		};

		mapaAkcija.put("cos", akcija);

		akcija = new AbstractAction("tan(x)") {

			@Override
			public void actionPerformed(ActionEvent e) {
				izracunaj(new ITransformacija() {

					@Override
					public double transformiraj(double x) {

						Action a = mapaAkcija.get("sin");

						boolean b = a.isEnabled();

						a.setEnabled(!b);

						String ime = a.getValue(NAME).toString();
						a.putValue(NAME, ime + "!");
						return Math.tan(x);
					}
				});
			}
		};

		mapaAkcija.put("tan", akcija);

		akcija = new AbstractAction("e^(x)") {

			@Override
			public void actionPerformed(ActionEvent e) {
				izracunaj(new ITransformacija() {

					@Override
					public double transformiraj(double x) {
						return Math.exp(x);
					}
				});
			}
		};

		mapaAkcija.put("e^x", akcija);

		akcija = new AbstractAction("log(x)") {

			@Override
			public void actionPerformed(ActionEvent e) {
				izracunaj(new ITransformacija() {

					@Override
					public double transformiraj(double x) {
						return Math.log10(x);
					}
				});
			}
		};

		mapaAkcija.put("log", akcija);

		akcija = new AbstractAction("ln(x)") {

			@Override
			public void actionPerformed(ActionEvent e) {
				izracunaj(new ITransformacija() {

					@Override
					public double transformiraj(double x) {
						return Math.log(x);
					}
				});
			}
		};

		mapaAkcija.put("ln", akcija);
	}

	private void izracunaj(ITransformacija transf) {
		String text = broj.getText();
		double x = 0;
		try {
			x = Double.parseDouble(text);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(Program3.this, "NaNaNaNaNaN batman!",
					"Pogreska", JOptionPane.ERROR_MESSAGE);
			return;
		}

		double rezultat = transf.transformiraj(x);

		broj.setText(String.valueOf(rezultat));
	}

	private static interface ITransformacija {
		double transformiraj(double x);
	}

	// Inicijalizacija programa ...
	public static void main(String[] args) {
		System.out.println("Metodu main izvodi dretva "
				+ Thread.currentThread().getName());
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				System.out.println("Metodu main izvodi dretva "
						+ Thread.currentThread().getName());
				startGUIApp();

			}
		});
	}

	protected static void startGUIApp() {
		new Program3().setVisible(true);

	}
}
