package com.blogspot.applications4android.comicreader.test.core;

import java.io.File;

import com.blogspot.applications4android.comicreader.core.Cache;
import com.blogspot.applications4android.comicreader.core.FileUtils;

import android.test.InstrumentationTestCase;


public class CacheTest extends InstrumentationTestCase {

	public void testGlobalStaticVars() {
		assertEquals("cache", Cache.CACHE);
		assertEquals(60, Cache.MAX_FILES);
		assertEquals(30, Cache.MIN_FILES);
	}

	public void testCacheFunctionality() {
		File root = new File(FileUtils.getSdcard(), "cache_test");
		Cache ca = new Cache(root);
		assertTrue(root.exists());
		assertTrue(root.isDirectory());
		assertEquals(root.getPath(), ca.cachePath());
		// create files
		for(int i=Cache.MAX_FILES+10;i>0;--i) {
			File file = new File(root, "file_"+i);
			FileUtils.touch(file.getPath());
		}
		ca.makeSpace();
		assertEquals(Cache.MIN_FILES, ca.numFiles());
		ca.clearCache();
		assertEquals(0, ca.numFiles());
		root.delete();
		assertFalse(root.exists());
	}

}
