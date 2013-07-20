package hr.fer.zemris.java.tecaj2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class Program2 extends JFrame {

	public Program2() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Moj prozor!");
		setLocation(10, 10);
		setSize(300, 400);
		initGUI();
	}

	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());

		MojaKomponenta mk = new MojaKomponenta();
		mk.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
		getContentPane().add(mk, BorderLayout.CENTER);

	}

	private static class MojaKomponenta extends JComponent {

		private BufferedImage slika;

		public MojaKomponenta() {
			Class<?> opsinik = this.getClass();
			InputStream is = opsinik.getResourceAsStream("slika.png");
			try {
				slika = ImageIO.read(is);
				Graphics2D g = slika.createGraphics();
				g.setColor(Color.MAGENTA);
				g.drawLine(0, 0, slika.getWidth(), slika.getHeight());
				g.drawLine(slika.getWidth(), 0, 0, slika.getHeight());
				g.dispose();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		@Override
		protected void paintComponent(Graphics g) {
			g.setColor(Color.RED);
			Dimension dim = getSize();
			Insets ins = getInsets();

			g.drawImage(slika, ins.left, ins.top, dim.width - ins.right,
					dim.height - ins.bottom, 0, 0, slika.getWidth(),
					slika.getHeight(), null);

			g.drawLine(ins.left, ins.top, (dim.width - ins.left - ins.right)
					/ 2 + ins.left, ins.top);
			g.setColor(Color.ORANGE);
			g.fillRect(20, 20, 40, 60);
			g.setColor(Color.BLACK);
			g.drawRect(20, 20, 40, 60);

			FontMetrics fm = g.getFontMetrics();
			g.drawString("Ovo je poruka", 80, 0 + ins.top + 1 + fm.getAscent());

			g.drawString("Ovo je 2. poruka", 80,
					0 + ins.top + 1 + fm.getAscent() + fm.getHeight());

			g.drawString("Ovo je 3. poruka", 80,
					0 + ins.top + 1 + fm.getAscent() + 2 * fm.getHeight());

			String poruka = "Hello world!";
			int sirinaPoruke = fm.stringWidth(poruka);
			int x = dim.width - ins.left - ins.right;
			x /= 2;
			x += ins.left;
			x -= sirinaPoruke / 2;
			g.drawString(poruka, x,
					ins.top + 1 + fm.getAscent() + 3 * fm.getHeight());

		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Program2().setVisible(true);

			}
		});

	}

}
