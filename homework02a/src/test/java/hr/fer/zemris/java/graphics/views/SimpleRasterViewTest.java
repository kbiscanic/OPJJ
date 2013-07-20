package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;
import hr.fer.zemris.java.graphics.raster.BWRasterMem;
import hr.fer.zemris.java.graphics.shapes.Circle;

import org.junit.Test;

public class SimpleRasterViewTest {

	@Test
	public final void testSimpleRasterViewCharChar() {
		SimpleRasterView view = new SimpleRasterView('a', 'g');
		BWRaster raster = new BWRasterMem(4, 4);
		new Circle(0, 0, 1).draw(raster);
		view.produce(raster);
	}

	@Test
	public final void testSimpleRasterView() {
		SimpleRasterView view = new SimpleRasterView();
		BWRaster raster = new BWRasterMem(2, 1);
		view.produce(raster);
	}

}
