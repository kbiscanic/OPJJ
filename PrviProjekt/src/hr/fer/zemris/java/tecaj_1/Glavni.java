package hr.fer.zemris.java.tecaj_1;

public class Glavni {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GeometrijskiLik lik1 = new GeometrijskiLik("Lik1");
		GeometrijskiLik lik2 = new GeometrijskiLik("Lik2");
		
		System.out.println("Ime lika 1 je: " + lik1.getIme());
		System.out.println("Ime lika 2 je: " + lik2.getIme());
		
		Linija l1 = new Linija(10, 10, 20, 20);
		System.out.println("Površina linije 1 je: " + l1.getPovrsina());
		System.out.println("Ime linije 1 je: " + l1.getIme());
		
		Pravokutnik p = new Pravokutnik(10, 10, 20, 10);
		System.out.println("Površina pravokutnika 1 je: " + p.getPovrsina());
		System.out.println("Ime pravokutnika 1 je: " + p.getIme());
		System.out.println("Opseg pravoktunika 1 je: " + p.getOpseg());
	}
}
