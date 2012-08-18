package com.blogspot.applications4android.comicreader.test.core;

import com.blogspot.applications4android.comicreader.core.RandUtils;

import junit.framework.TestCase;


public class RandUtilsTest extends TestCase {

	public void testRandUtils() {
		assertNotNull(RandUtils.getObj());
		assertNotNull(RandUtils.getRNG());
		assertTrue(RandUtils.getPositiveInt() >= 0);
		int limit = 100;
		int first = 10;
		int i = RandUtils.getPositiveInt(limit);
		assertTrue((i >= 0) && (i < limit));
		i = RandUtils.getPositiveInt(limit, first);
		int last = first + limit - 1;
		assertTrue((i >= first) && (i < last));
		long limitL = 100;
		long firstL = 10;
		long iL = RandUtils.getPositiveLong(limitL);
		assertTrue((iL >= 0) && (iL < limitL));
		iL = RandUtils.getPositiveLong(limitL, firstL);
		long lastL = firstL + limitL - 1;
		assertTrue((iL >= firstL) && (iL < lastL));
	}

}
