package hr.fer.zemris.java.graphics.raster;

import junit.framework.Assert;

import org.junit.Test;

public class BWRasterMemTest {

	@Test
	public final void testBWRasterMem() {
		boolean exc = false;
		try {
			new BWRasterMem(-1, -1);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		exc = false;
		try {
			new BWRasterMem(-1, 1);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		exc = false;
		try {
			new BWRasterMem(1, -1);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);

		BWRaster bwrm2 = new BWRasterMem(10, 10);
		Assert.assertEquals(10, bwrm2.getHeight());
	}

	@Test
	public final void testGetWidth() {
		BWRaster bwrm = new BWRasterMem(10, 5);
		Assert.assertEquals(10, bwrm.getWidth());
	}

	@Test
	public final void testGetHeight() {
		BWRaster bwrm = new BWRasterMem(10, 5);
		Assert.assertEquals(5, bwrm.getHeight());
	}

	@Test
	public final void testTurnOn() {
		BWRasterMem bwrm = new BWRasterMem(10, 10);
		boolean exc = false;
		try {
			bwrm.turnOn(-1, -1);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		exc = false;
		try {
			bwrm.turnOn(-1, 1);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		try {
			bwrm.turnOn(1, -1);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		try {
			bwrm.turnOn(100, 1);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		try {
			bwrm.turnOn(1, 100);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		try {
			bwrm.turnOn(-1, 100);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		try {
			bwrm.turnOn(100, -1);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);

		bwrm.turnOn(0, 0);
		Assert.assertTrue(bwrm.isTurnedOn(0, 0));
		bwrm.enableFlipMode();
		bwrm.turnOn(0, 0);
		Assert.assertFalse(bwrm.isTurnedOn(0, 0));
		bwrm.disableFlipMode();
		bwrm.turnOn(0, 0);
		Assert.assertTrue(bwrm.isTurnedOn(0, 0));
	}

	@Test
	public final void testTurnOff() {
		BWRasterMem bwrm = new BWRasterMem(10, 10);
		boolean exc = false;
		try {
			bwrm.turnOff(-1, -1);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		exc = false;
		try {
			bwrm.turnOff(-1, 1);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		try {
			bwrm.turnOff(1, -1);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		try {
			bwrm.turnOff(100, 1);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		try {
			bwrm.turnOff(1, 100);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		try {
			bwrm.turnOff(-1, 100);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		try {
			bwrm.turnOff(100, -1);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);

		bwrm.turnOn(0, 0);
		bwrm.turnOff(0, 0);
		Assert.assertFalse(bwrm.isTurnedOn(0, 0));
	}

	@Test
	public final void testIsTurnedOn() {
		BWRasterMem bwrm = new BWRasterMem(10, 10);
		boolean exc = false;
		try {
			bwrm.isTurnedOn(-1, -1);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		exc = false;
		try {
			bwrm.isTurnedOn(-1, 1);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		try {
			bwrm.isTurnedOn(1, -1);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		try {
			bwrm.isTurnedOn(100, 1);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		try {
			bwrm.isTurnedOn(1, 100);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		try {
			bwrm.isTurnedOn(-1, 100);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
		try {
			bwrm.isTurnedOn(100, -1);
		} catch (IllegalArgumentException ex) {
			exc = true;
		}
		Assert.assertTrue(exc);
	}

}
