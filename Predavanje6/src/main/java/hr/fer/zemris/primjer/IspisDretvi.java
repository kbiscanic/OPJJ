package hr.fer.zemris.primjer;

public class IspisDretvi {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Thread trenutna = Thread.currentThread();

		System.out.println("Moje ime je: " + trenutna.getName());
		System.out.println("Ja sam demonska: " + trenutna.isDaemon());
		System.out.println("Ja sam živa: " + trenutna.isAlive());
		System.out.println("Moj prio je: " + trenutna.getPriority() + "\n");

		ThreadGroup grupa = trenutna.getThreadGroup();
		while (grupa.getParent() != null) {
			grupa = grupa.getParent();
		}

		grupa.list();

	}
}
