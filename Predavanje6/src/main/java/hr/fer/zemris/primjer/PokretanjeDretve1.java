package hr.fer.zemris.primjer;

public class PokretanjeDretve1 {

	public static void main(String[] args) {

		Thread[] dretve = new MojPosao[5];

		for (int i = 0; i < dretve.length; i++) {
			dretve[i] = new MojPosao("Radnik " + (i + 1));
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

		System.out.println("Ja sam zadnja živa dretva!");

	}

	static class MojPosao extends Thread {

		@Override
		public void run() {
			System.out.println("Pozdrav iz dretve: "
					+ Thread.currentThread().getName());

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Dretva završava: "
					+ Thread.currentThread().getName());
		}

		public MojPosao(String name) {
			super(name);
		}

	}

}
