package hr.fer.zemris.java.tecaj_3;

import junit.framework.Assert;

import org.junit.Test;

public class LikeMedianTest {

	@Test
	public void testLikeMedian() {
		LikeMedian<Integer> likeMedian = new LikeMedian<Integer>();
		likeMedian.add(new Integer(10));
		likeMedian.add(new Integer(5));
		likeMedian.add(new Integer(3));
		Assert.assertEquals(new Integer(5), likeMedian.get());
	}

}
