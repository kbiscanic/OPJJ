package hr.fer.zemris.primjer.strategija;

public interface IObrada<T> {

	int brojStupaca();

	void obradiRedak(String[] elems);

	T dohvatiRezultat();

}
