package hr.fer.zemris.java.tecaj2;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

@SuppressWarnings("serial")
public class Program1 extends JFrame {

	public Program1() throws HeadlessException {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Moj prozor!");
		setLocation(10, 10);
		setSize(300, 400);
		initGUI();
	}

	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());

		// ListModel<Integer> model1 = new ParniBrojevi(0, 5);
		final VarijabilniModel model2 = new VarijabilniModel();
		model2.dodaj(5);
		model2.dodaj(7);
		model2.dodaj(-6);
		model2.dodaj(21);
		model2.dodaj(42);

		final JList<Integer> lista1 = new JList<>(model2);
		final JList<Integer> lista2 = new JList<>(model2);
		BrojElemenata<Integer> brojElemenata = new BrojElemenata<>(model2);

		JSplitPane splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new JScrollPane(lista1), new JScrollPane(lista2));
		splitter.setDividerLocation(130);

		final JTextField tfBroj = new JTextField();
		final JButton btnDodaj = new JButton("Dodaj");
		final JButton btnObrisi = new JButton("Obrisi");

		JPanel panel = new JPanel(new GridLayout(1, 3));
		panel.add(tfBroj);
		panel.add(btnDodaj);
		panel.add(btnObrisi);

		btnDodaj.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String uneseniTekst = tfBroj.getText();
				try {
					Integer vrijednost = Integer.valueOf(uneseniTekst);
					model2.dodaj(vrijednost);
					tfBroj.setText("");
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(Program1.this,
							"NaNaNaNaN Batman!", "Pogreska",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		btnObrisi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int oznaceniIndeks = lista2.getSelectedIndex();
				if (oznaceniIndeks == -1) {
					return;
				}
				model2.obrisi(oznaceniIndeks);

			}
		});

		getContentPane().add(splitter, BorderLayout.CENTER);
		getContentPane().add(brojElemenata, BorderLayout.PAGE_START);
		getContentPane().add(panel, BorderLayout.PAGE_END);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Program1().setVisible(true);

			}
		});

	}

	private static class ParniBrojevi implements ListModel<Integer> {

		private int prvi;
		private int n;

		public ParniBrojevi(int prvi, int n) {
			this.prvi = prvi;
			this.n = n;
		}

		@Override
		public int getSize() {
			return n;
		}

		@Override
		public Integer getElementAt(int index) {
			return prvi + index * 2;
		}

		@Override
		public void addListDataListener(ListDataListener l) {
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
		}

	}

	private static class VarijabilniModel implements ListModel<Integer> {
		private List<Integer> podatci = new ArrayList<>();
		private List<ListDataListener> promatraci = new ArrayList<>();

		@Override
		public int getSize() {
			return podatci.size();
		}

		public void obrisi(int index) {
			podatci.remove(index);
			ListDataEvent opis = new ListDataEvent(this,
					ListDataEvent.INTERVAL_REMOVED, index, index);
			ListDataListener[] kopija = new ListDataListener[promatraci.size()];
			promatraci.toArray(kopija);
			for (ListDataListener l : kopija) {
				l.intervalRemoved(opis);
			}
		}

		public void dodaj(Integer value) {
			podatci.add(value);
			int pozicija = podatci.size() - 1;
			ListDataEvent opis = new ListDataEvent(this,
					ListDataEvent.INTERVAL_ADDED, pozicija, pozicija);
			for (ListDataListener l : promatraci) {
				l.intervalAdded(opis);
			}
		}

		@Override
		public Integer getElementAt(int index) {
			return podatci.get(index);
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			System.out.println("Imam registraciju: " + l);
			promatraci.add(l);
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			promatraci.remove(l);
		}

	}

	private static class BrojElemenata<E> extends JLabel {
		private ListModel<E> modelListe;

		public BrojElemenata(ListModel<E> modelListe) {
			super();
			this.modelListe = modelListe;
			modelListe.addListDataListener(new ListDataListener() {

				@Override
				public void intervalRemoved(ListDataEvent e) {
					azurirajTekst();
					BrojElemenata.this.modelListe.removeListDataListener(this);

				}

				@Override
				public void intervalAdded(ListDataEvent e) {
					azurirajTekst();

				}

				@Override
				public void contentsChanged(ListDataEvent e) {
					azurirajTekst();
				}
			});
			azurirajTekst();
		}

		private void azurirajTekst() {
			setText("Broj elemenata liste: " + modelListe.getSize() + ".");

		}

	}
}
