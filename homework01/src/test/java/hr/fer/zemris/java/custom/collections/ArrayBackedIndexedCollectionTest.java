package hr.fer.zemris.java.custom.collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit test file used to test class {@link ArrayBackedIndexedCollection}.
 * @author Kristijan Biscanic
 * @version 1.0
 */
public class ArrayBackedIndexedCollectionTest {

	@Test
	public void testArrayBackedIndexedCollection() {
		ArrayBackedIndexedCollection col = new ArrayBackedIndexedCollection();
		Assert.assertEquals(0, col.size());
	}

	@Test
	public void testArrayBackedIndexedCollectionInt() {
		ArrayBackedIndexedCollection col = new ArrayBackedIndexedCollection(2);
		Assert.assertEquals(0, col.size());

		boolean exceptioned = false;
		try {
			new ArrayBackedIndexedCollection(-3);
		} catch (IllegalArgumentException ex) {
			exceptioned = true;
		}

		Assert.assertTrue(exceptioned);
	}

	@Test
	public void testIsEmpty() {
		ArrayBackedIndexedCollection col = new ArrayBackedIndexedCollection();
		Assert.assertTrue(col.isEmpty());
		col.add("New York");
		Assert.assertFalse(col.isEmpty());
	}

	@Test
	public void testSize() {
		ArrayBackedIndexedCollection col = new ArrayBackedIndexedCollection();
		Assert.assertEquals(0, col.size());
		col.add("New York");
		Assert.assertEquals(1, col.size());

	}

	@Test
	public void testAdd() {
		ArrayBackedIndexedCollection col = new ArrayBackedIndexedCollection(1);

		boolean exceptioned = false;
		try {
			col.add(null);
		} catch (IllegalArgumentException ex) {
			exceptioned = true;
		}

		Assert.assertTrue(exceptioned);

		col.add("New York");
		col.add("San Francisco");

		Assert.assertEquals(2, col.size());

	}

	@Test
	public void testGet() {
		ArrayBackedIndexedCollection col = new ArrayBackedIndexedCollection();

		boolean exceptioned = false;
		try {
			col.get(-1);
		} catch (IndexOutOfBoundsException ex) {
			exceptioned = true;
		}

		Assert.assertTrue(exceptioned);

		exceptioned = false;
		try {
			col.get(4);
		} catch (IndexOutOfBoundsException ex) {
			exceptioned = true;
		}

		Assert.assertTrue(exceptioned);

		col.add("New York");
		Assert.assertEquals("New York", col.get(0));
	}

	@Test
	public void testRemove() {
		ArrayBackedIndexedCollection col = new ArrayBackedIndexedCollection();

		boolean exceptioned = false;
		try {
			col.remove(-1);
		} catch (IndexOutOfBoundsException ex) {
			exceptioned = true;
		}

		Assert.assertTrue(exceptioned);

		exceptioned = false;
		try {
			col.remove(4);
		} catch (IndexOutOfBoundsException ex) {
			exceptioned = true;
		}

		Assert.assertTrue(exceptioned);

		col.add("New York");
		col.remove(0);
		Assert.assertTrue(col.isEmpty());

		col.add("New York");
		col.add("San Francisco");
		col.remove(0);
		Assert.assertEquals("San Francisco", col.get(0));
	}

	@Test
	public void testInsert() {
		ArrayBackedIndexedCollection col = new ArrayBackedIndexedCollection(1);

		boolean exceptioned = false;
		try {
			col.insert(null, 0);
		} catch (IllegalArgumentException ex) {
			exceptioned = true;
		}

		Assert.assertTrue(exceptioned);

		exceptioned = false;
		try {
			col.insert("New York", -1);
		} catch (IllegalArgumentException ex) {
			exceptioned = true;
		}

		exceptioned = false;
		try {
			col.insert("New York", 4);
		} catch (IllegalArgumentException ex) {
			exceptioned = true;
		}

		Assert.assertTrue(exceptioned);

		Assert.assertTrue(exceptioned);
		col.insert("New York", 0);
		col.insert("San Francisco", 0);
		Assert.assertEquals(2, col.size());
	}

	@Test
	public void testIndexOf() {
		ArrayBackedIndexedCollection col = new ArrayBackedIndexedCollection();

		col.add("New York");
		col.add("San Francisco");
		col.add("Los Angeles");

		Assert.assertEquals(0, col.indexOf("New York"));
		Assert.assertEquals(2, col.indexOf("Los Angeles"));
		Assert.assertEquals(-1, col.indexOf("Zagreb"));

	}

	@Test
	public void testContains() {
		ArrayBackedIndexedCollection col = new ArrayBackedIndexedCollection();

		col.add("New York");
		col.add("San Francisco");
		col.add("Los Angeles");

		Assert.assertTrue(col.contains("New York"));
		Assert.assertTrue(col.contains("Los Angeles"));
		Assert.assertFalse(col.contains("Zagreb"));
	}

	@Test
	public void testClear() {
		ArrayBackedIndexedCollection col = new ArrayBackedIndexedCollection();

		col.add("New York");
		col.add("San Francisco");
		col.add("Los Angeles");

		col.clear();

		Assert.assertFalse(col.contains("New York"));
	}

}
