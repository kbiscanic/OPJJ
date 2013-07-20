package hr.fer.zemris.java.tecaj;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class Program2 extends JFrame {

	public Program2() {
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setTitle("Moj prvi prozor");
		this.setLocation(10, 10);
		this.setSize(500, 200);

		initGUI();
	}

	private void initGUI() {
		this.getContentPane().setLayout(null);

		JLabel labela = new JLabel("Hello world!");
		labela.setLocation(20, 10);
		labela.setSize(labela.getPreferredSize());
		labela.setOpaque(true);
		labela.setBackground(Color.YELLOW);
		labela.setForeground(Color.BLUE);

		JButton gumb1 = new JButton("Stisni me");
		gumb1.setLocation(20, 30);
		gumb1.setSize(gumb1.getPreferredSize());
		gumb1.addActionListener(new MojPosao1());

		JButton gumb2 = new JButton("Ne stisni me");
		gumb2.setLocation(gumb1.getLocation().x + gumb1.getWidth() + 10,
				gumb1.getLocation().y);
		gumb2.setSize(gumb2.getPreferredSize());
		gumb2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Korisnik je stisnuo drugi gumb!");

			}
		});

		this.getContentPane().add(labela);
		this.getContentPane().add(gumb1);
		this.getContentPane().add(gumb2);
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
		new Program2().setVisible(true);

	}

	static class MojPosao1 implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Korisnik je stisnuo prvi gumb!");

		}

	}
}
