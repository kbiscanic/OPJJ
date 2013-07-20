package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

import junit.framework.Assert;

import org.junit.Test;

public class ObjectMultistackTest {

	@Test
	public void testObjectMultistack() {
		ObjectMultistack multiStack = new ObjectMultistack();
		ValueWrapper value = new ValueWrapper(Integer.valueOf(2013));
		multiStack.push("year", value);
		Assert.assertEquals(2013, multiStack.peek("year").getValue());
	}

	@Test
	public void testPush() {
		ObjectMultistack multiStack = new ObjectMultistack();
		ValueWrapper value = new ValueWrapper(Integer.valueOf(2013));
		multiStack.push("year", value);
		ValueWrapper value1 = new ValueWrapper(Integer.valueOf(2014));
		multiStack.push("year", value1);
		Assert.assertEquals(2014, multiStack.pop("year").getValue());
		Assert.assertEquals(2013, multiStack.peek("year").getValue());
	}

	@Test(expected = NoSuchElementException.class)
	public void testPopException() {
		ObjectMultistack multiStack = new ObjectMultistack();
		multiStack.pop("year");
	}

	@Test(expected = EmptyStackException.class)
	public void testPopException2() {
		ObjectMultistack multiStack = new ObjectMultistack();
		multiStack.push("year", new ValueWrapper(Integer.valueOf(1)));
		multiStack.push("year", new ValueWrapper(Integer.valueOf(2)));

		multiStack.pop("year");
		multiStack.pop("year");
		multiStack.pop("year");
	}

	@Test(expected = NoSuchElementException.class)
	public void testPeekException() {
		ObjectMultistack multiStack = new ObjectMultistack();
		multiStack.peek("year");
	}

	@Test(expected = EmptyStackException.class)
	public void testPeekException2() {
		ObjectMultistack multiStack = new ObjectMultistack();
		multiStack.push("year", new ValueWrapper(Integer.valueOf(1)));

		multiStack.pop("year");
		multiStack.peek("year");
	}

	@Test
	public void testIsEmpty() {
		ObjectMultistack multistack = new ObjectMultistack();
		multistack.push("year", new ValueWrapper(Integer.valueOf(2013)));
		Assert.assertFalse(multistack.isEmpty("year"));
		multistack.pop("year");
		Assert.assertTrue(multistack.isEmpty("year"));
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testEmptyException() {
		ObjectMultistack multiStack = new ObjectMultistack();
		multiStack.isEmpty("year");
	}

}
