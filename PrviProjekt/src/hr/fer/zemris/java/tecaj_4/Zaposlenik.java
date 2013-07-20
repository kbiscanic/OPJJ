package hr.fer.zemris.java.tecaj_4;

public class Zaposlenik implements Comparable<Zaposlenik> {

	private String sifra;
	private String ime;
	private String prezime;
	private String placa;

	public Zaposlenik(String sifra, String ime, String prezime, String placa) {
		super();
		this.sifra = sifra;
		this.ime = ime;
		this.prezime = prezime;
		this.placa = placa;
	}

	public String getSifra() {
		return sifra;
	}

	public String getIme() {
		return ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public String getPlaca() {
		return placa;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Zaposlenik)) {
			return false;
		}
		Zaposlenik drugi = (Zaposlenik) obj;
		return this.sifra.equals(drugi.sifra);
	}

	@Override
	public int compareTo(Zaposlenik o) {
		return this.sifra.compareTo(o.sifra);
	}

	@Override
	public int hashCode() {
		return sifra.hashCode();
	}

}
