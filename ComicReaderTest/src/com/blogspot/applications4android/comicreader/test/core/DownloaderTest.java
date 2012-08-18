package com.blogspot.applications4android.comicreader.test.core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.net.URI;
import junit.framework.TestCase;

import com.blogspot.applications4android.comicreader.core.Downloader;
import com.blogspot.applications4android.comicreader.core.FileUtils;


public class DownloaderTest extends TestCase {

	public void testDownloadToString() {
		try {
			String resp = Downloader.downloadToString(new URI("http://www.google.com"));
			assertTrue(resp.indexOf("<!doctype html>") != -1);
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testOpenConnection() {
		try {
			BufferedReader br = Downloader.openConnection(new URI("http://www.google.com"));
			assertNotNull(br);
			br.close();
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testOpenConnectionStream() {
		try {
			BufferedInputStream bis = Downloader.openConnectionStream(new URI("http://www.google.com"));
			assertNotNull(bis);
			bis.close();
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testDnldImage() {
		File img = new File(FileUtils.getSdcard(), "wilber.png");
		try {
			Downloader.dnldImage(img.getPath(), new URI("http://www.gimp.org/images/news-icons/wilber.png"));
			assertTrue(img.exists());
			assertEquals(4413, img.length());
			assertTrue(FileUtils.delete(img));   // testing FileUtils.delete function here!
			assertFalse(img.exists());
		}
		catch(Exception e) {
			fail(e.toString());
		}
	}

	public void testDecodeHtml() {
		assertEquals("&-", Downloader.decodeHtml("&#amp;&#8211;"));
		assertEquals("abcd;", Downloader.decodeHtml("abcd;"));
	}

}
