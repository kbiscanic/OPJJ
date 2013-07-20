package hr.fer.zemris.java.tecaj_1;

import java.util.Iterator;

public class ParniBrojevi implements Iterable {

	private int prvi;
	private int n;

	public ParniBrojevi(int prvi, int n) {
		super();
		this.prvi = prvi;
		this.n = n;
	}

	@Override
	public Iterator iterator() {
		return new MojIterator();
	}

	private class MojIterator implements Iterator {

		private int trenutni;
		private int preostalo;

		public MojIterator() {
			this.trenutni = ParniBrojevi.this.prvi;
			preostalo = ParniBrojevi.this.n;
		}

		@Override
		public boolean hasNext() {
			return preostalo > 0;
		}

		@Override
		public Object next() {
			if (preostalo < 1) {
				throw new RuntimeException("Nema više elemenata");
			}
			int zaVratiti = trenutni;
			trenutni += 2;
			preostalo--;
			return Integer.valueOf(zaVratiti);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException(
					"Ne možete brisati parne brojeve!");

		}

	}

}
