package com.blogspot.applications4android.comicreader.test.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.blogspot.applications4android.comicreader.core.FileUtils;
import com.blogspot.applications4android.comicreader.exceptions.ComicSDCardFull;

import android.content.res.AssetManager;
import android.test.InstrumentationTestCase;

public class FileUtilsTest extends InstrumentationTestCase {

	public void testGlobalStaticVars() {
		assertEquals(8192, FileUtils.BUFF_SIZE);
		assertEquals(512, FileUtils.SB_CAPACITY);
		assertEquals(5, FileUtils.MIN_BYTES_SDCARD_MB);
		assertEquals("ComicReader", FileUtils.COMIC_ROOT);
	}

	public void testTouch() {
		assertTrue(FileUtils.touch("/sdcard/file1"));
		File f = new File("/sdcard/file1");
		assertTrue(f.exists());
		f.delete();
	}

	public void testNumLines() {
		try {
			AssetManager mgr = getInstrumentation().getContext().getAssets();
			assertEquals(6, FileUtils.numLines(new InputStreamReader(mgr.open("FileUtilsTest/lines.txt"))));
		}
		catch(Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	public void testSlurp() {
		try {
			AssetManager mgr = getInstrumentation().getContext().getAssets();
			assertEquals("hello world", FileUtils.slurp("FileUtilsTest/slurp.txt", mgr));
		}
		catch(Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	public void testStoreString() {
		File f = new File(FileUtils.getSdcard(), "temp.txt");
		try {
			String s = "temporary string stored";
			FileUtils.storeString(s, f);
			assertTrue(f.exists());
			assertEquals(s, FileUtils.slurp(new FileInputStream(f)));
			f.delete();
		}
		catch(Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	public void testGetTempFile() {
		assertTrue(FileUtils.getTempFile("abc", "xyz").matches("^abc.*xyz$"));
		assertTrue(FileUtils.getTempFile().matches(".*\\.temp$"));
	}

	public void testCheckFreeSpaceSdcard() {
		try {
			FileUtils.checkFreeSpaceSdcard();
			assertTrue(true);
		}
		catch (ComicSDCardFull e) {
			fail();
		}
	}

	public void testSdcardAvailableBytesMB() {
		assertTrue(FileUtils.sdcardAvailableBytesMB() > 0);
	}

	public void testGetSdcard() {
		assertNotNull(FileUtils.getSdcard());
	}

	public void testGetComicRoot() {
		assertNotNull(FileUtils.getComicRoot());
	}

	public void testMkdir() {
		File f = new File(FileUtils.getSdcard(), "folder1");
		assertFalse(f.exists());
		assertTrue(FileUtils.mkdir(f));
		assertTrue(f.exists());
		assertTrue(f.isDirectory());
		f.delete();
	}

	public void testCopyFile() {
		File src = new File(FileUtils.getSdcard(), "file1");
		File dst = new File(FileUtils.getSdcard(), "file2");
		try {
			FileUtils.storeString("abcdefghij", src);
			assertTrue(src.exists());
			assertFalse(dst.exists());
			assertTrue(FileUtils.copyFile(src, dst));
			assertTrue(dst.exists());
			assertEquals(src.length(), dst.length());
			assertEquals(FileUtils.slurp(new FileInputStream(src)),
						 FileUtils.slurp(new FileInputStream(dst)));
			src.delete();
			dst.delete();
		}
		catch(Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
}
