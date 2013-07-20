package hr.fer.zemris.java.custom.collections;

import junit.framework.Assert;

import org.junit.Test;

/**
 * JUnit test file used to test class {@link ObjectStack}. 
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class ObjectStackTest {

	@Test
	public void testObjectStack() {
		ObjectStack os = new ObjectStack();
		Assert.assertTrue(os.isEmpty());
	}

	@Test
	public void testObjectStackInt() {
		ObjectStack os = new ObjectStack(2);
		Assert.assertTrue(os.isEmpty());
	}

	@Test
	public void testIsEmpty() {
		ObjectStack os = new ObjectStack();
		Assert.assertTrue(os.isEmpty());
		os.push(1);
		Assert.assertFalse(os.isEmpty());
	}

	@Test
	public void testPush() {
		ObjectStack os = new ObjectStack();
		os.push(1);
		os.push(2);
		Assert.assertFalse(os.isEmpty());
	}

	@Test
	public void testPop() {
		ObjectStack os = new ObjectStack();

		boolean exceptioned = false;
		try {
			os.pop();
		} catch (EmptyStackException ex) {
			exceptioned = true;
		}
		Assert.assertTrue(exceptioned);
		os.push(1);
		Assert.assertEquals(1, os.pop());
	}

	@Test
	public void testPeek() {
		ObjectStack os = new ObjectStack();

		boolean exceptioned = false;
		try {
			os.peek();
		} catch (EmptyStackException ex) {
			exceptioned = true;
		}
		Assert.assertTrue(exceptioned);
		os.push(1);
		Assert.assertEquals(1, os.peek());
	}

	@Test
	public void testClear() {
		ObjectStack os = new ObjectStack();
		os.push(1);
		os.push(2);
		os.clear();
		Assert.assertTrue(os.isEmpty());
	}

}
