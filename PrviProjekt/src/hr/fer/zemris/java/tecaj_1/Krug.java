package hr.fer.zemris.java.tecaj_1;

public class Krug extends GeometrijskiLik {
	private int cenX;
	private int cenY;
	private int rad;

	public Krug(int cenX, int cenY, int rad) {
		super("Krug");
		this.cenX = cenX;
		this.cenY = cenY;
		this.rad = rad;
	}

	@Override
	public double getOpseg() {
		return 2 * rad * Math.PI;
	}

	@Override
	public double getPovrsina() {
		return rad * rad * Math.PI;
	}

	@Override
	public boolean sadrziTocku(int x, int y) {
		return (cenX - x) * (cenX - x) + (cenY - y) * (cenY - y) <= rad * rad;
	}
}
