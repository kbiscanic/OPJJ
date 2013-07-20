package hr.fer.zemris.java.tecaj2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class Program3 extends JFrame {

	public Program3() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Moj prozor!");
		setLocation(10, 10);
		setSize(300, 400);
		initGUI();
	}

	private static class Linija {
		private Point p1;
		private Point p2;

		public Linija(Point p1, Point p2) {
			super();
			this.p1 = p1;
			this.p2 = p2;
		}

	}

	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());

		MojaKomponenta mk = new MojaKomponenta();
		getContentPane().add(mk, BorderLayout.CENTER);

	}

	private static class MojaKomponenta extends JComponent {

		private List<Linija> linije = new ArrayList<>();
		private Point pocetak;
		private Point kraj;

		public MojaKomponenta() {
			addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if (pocetak == null) {
						pocetak = e.getPoint();
					} else {
						kraj = e.getPoint();
						linije.add(new Linija(pocetak, kraj));
						pocetak = null;
						kraj = null;
						repaint();
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
				}

				@Override
				public void mouseClicked(MouseEvent e) {
				}
			});
			addMouseMotionListener(new MouseMotionListener() {

				@Override
				public void mouseMoved(MouseEvent e) {
					if (pocetak != null) {
						kraj = e.getPoint();
						repaint();
					}

				}

				@Override
				public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			});
		}

		@Override
		protected void paintComponent(Graphics g) {

			g.setColor(Color.RED);
			for (Linija l : linije) {
				g.drawLine(l.p1.x, l.p1.y, l.p2.x, l.p2.y);
			}

			if (pocetak != null) {
				g.setColor(Color.GREEN);
				g.drawLine(pocetak.x, pocetak.y, kraj.x, kraj.y);
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Program3().setVisible(true);

			}
		});

	}

}
