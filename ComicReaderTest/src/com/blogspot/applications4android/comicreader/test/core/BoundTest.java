package com.blogspot.applications4android.comicreader.test.core;

import com.blogspot.applications4android.comicreader.core.Bound;

import junit.framework.TestCase;


public class BoundTest extends TestCase {

	public void testBound() {
		// handy constructor
		Bound b1 = new Bound(10, 20);
		assertTrue(b1.isUnderLimit(15));
		assertFalse(b1.isUnderLimit(9));
		assertEquals(10, b1.min);
		assertEquals(20, b1.max);
		// default constructor
		Bound b2 = new Bound();
		assertFalse(b2.isUnderLimit(1));
		b2.min = 10;
		b2.max = 20;
		assertTrue(b2.isUnderLimit(15));
		assertFalse(b2.isUnderLimit(9));
		assertEquals(10, b2.min);
		assertEquals(20, b2.max);
	}

}
