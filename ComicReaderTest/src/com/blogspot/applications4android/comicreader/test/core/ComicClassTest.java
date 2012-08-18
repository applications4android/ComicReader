package com.blogspot.applications4android.comicreader.test.core;

import com.blogspot.applications4android.comicreader.core.ComicClass;

import junit.framework.TestCase;

public class ComicClassTest extends TestCase {

	public void testComicClass() {
		// default
		ComicClass c1 = new ComicClass();
		assertNull(c1.mClass);
		assertNull(c1.mName);
		assertNull(c1.mPref);
		assertFalse(c1.mNew);
		assertFalse(c1.mSel);
		// after initialization
		c1.mClass = "class";
		c1.mName = "ClassName";
		c1.mPref = "classPref";
		c1.mNew = true;
		c1.mSel = true;
		assertEquals("class", c1.mClass);
		assertEquals("ClassName", c1.mName);
		assertEquals("classPref", c1.mPref);
		assertTrue(c1.mNew);
		assertTrue(c1.mSel);
	}
}
