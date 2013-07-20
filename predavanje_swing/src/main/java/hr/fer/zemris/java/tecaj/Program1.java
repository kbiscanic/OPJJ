package hr.fer.zemris.java.tecaj;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Program1 {

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
		JFrame prozor = new JFrame();
		prozor.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		prozor.setTitle("Moj prvi prozor");
		prozor.setLocation(10, 10);
		prozor.setSize(500, 200);
		prozor.setVisible(true);

	}
}
