package hr.fer.zemris.primjer;

public class PokretanjeDretve3 {

	volatile static long brojac = 0;

	public static void main(String[] args) {

		final long brojUvecavanja = 1_000_000;

		Thread[] dretve = new Thread[5];

		for (int i = 0; i < dretve.length; i++) {
			dretve[i] = new Thread(new MojPosao(brojUvecavanja), "Radnik "
					+ (i + 1));
		}

		for (int i = 0; i < dretve.length; i++) {
			dretve[i].start();
		}

		for (int i = 0; i < dretve.length; i++) {
			try {
				dretve[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("Ja sam zadnja živa dretva, a brojač je: " + brojac);

	}

	static class MojPosao implements Runnable {

		private long brojUvecavanja;

		public MojPosao(long brojUvecavanja) {
			super();
			this.brojUvecavanja = brojUvecavanja;
		}

		@Override
		public void run() {
			for (long i = 0; i < brojUvecavanja; i++) {
				brojac++;
			}
		}
	}

}
