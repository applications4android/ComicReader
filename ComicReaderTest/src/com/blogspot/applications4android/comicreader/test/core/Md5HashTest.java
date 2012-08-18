package com.blogspot.applications4android.comicreader.test.core;

import com.blogspot.applications4android.comicreader.core.Md5Hash;

import junit.framework.TestCase;

public class Md5HashTest extends TestCase {

	public void testStr2md5() {
		assertEquals("900150983CD24FB0D6963F7D28E17F72", Md5Hash.str2md5("abc"));
		assertEquals("4ED9407630EB1000C0F6B63842DEFA7D", Md5Hash.str2md5("def"));
		assertNull(Md5Hash.str2md5(null));
	}

}
