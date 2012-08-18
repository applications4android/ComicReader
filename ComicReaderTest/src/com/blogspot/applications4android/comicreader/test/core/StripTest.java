package com.blogspot.applications4android.comicreader.test.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import org.json.JSONObject;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.test.InstrumentationTestCase;

import com.blogspot.applications4android.comicreader.core.ComicParser;
import com.blogspot.applications4android.comicreader.core.JsonUtils;
import com.blogspot.applications4android.comicreader.core.Strip;

public class StripTest extends InstrumentationTestCase {

	public void testConstructor1() {
		// constructor
		Strip s1 = new Strip("abc", "root");
		assertNotNull(s1);
		assertEquals("abc", s1.uid());
		assertEquals("root/900150983CD24FB0D6963F7D28E17F72.img", s1.getImagePath());
		assertEquals(false, s1.isRead());
		assertEquals(false, s1.isFavorite());
		assertNull(s1.getPrevious());
		assertNull(s1.getNext());
		assertNull(s1.getTitle());
		assertNull(s1.getText());
		assertFalse(s1.hasNext());
		assertFalse(s1.hasPrevious());
		assertFalse(s1.hasText());
		// set read
		s1.setAsRead(true);
		assertEquals(true, s1.isRead());
		// set favorite
		s1.setAsFavorite(true);
		assertEquals(true, s1.isFavorite());
		// set previous
		s1.setPrevious("xyz");
		assertEquals("xyz", s1.getPrevious());
		assertNull(s1.getNext());
		assertFalse(s1.hasNext());
		assertTrue(s1.hasPrevious());
		// set next
		s1.setNext("ghi");
		assertEquals("xyz", s1.getPrevious());
		assertEquals("ghi", s1.getNext());
		assertTrue(s1.hasNext());
		assertTrue(s1.hasPrevious());
		// set text
		s1.setText("Strip text");
		assertEquals("Strip text", s1.getText());
		assertTrue(s1.hasText());
		// set title
		s1.setTitle("Strip title");
		assertEquals("Strip title", s1.getTitle());
		// valid file name from set title
		assertEquals("Strip_title", s1.currentTitleAsValidFilename());
	}

	public void testConstructor2() {
		// constructor only
		Strip s1 = new Strip("abc", "root", true, true, "xyz", "ghi", "title", "-NA-");
		assertNotNull(s1);
		assertEquals("abc", s1.uid());
		assertEquals("root/900150983CD24FB0D6963F7D28E17F72.img", s1.getImagePath());
		assertEquals(true, s1.isRead());
		assertEquals(true, s1.isFavorite());
		assertEquals("xyz", s1.getPrevious());
		assertEquals("ghi", s1.getNext());
		assertEquals("title", s1.getTitle());
		assertEquals("-NA-", s1.getText());
		assertTrue(s1.hasNext());
		assertTrue(s1.hasPrevious());
		assertFalse(s1.hasText());
	}

	public void testReadFromJsonObject() {
		AssetManager am = getInstrumentation().getContext().getAssets();
		try {
			JSONObject root = JsonUtils.jsonRoot(am.open("StripTest/strip1.json"));
			assertNotNull(root);
			Strip s1 = Strip.readFromJsonObject(root);
			assertNotNull(s1);
			assertEquals("hurl", s1.uid());
			assertEquals("/sdcard/image.img", s1.getImagePath());
			assertEquals(false, s1.isRead());
			assertEquals(true, s1.isFavorite());
			assertEquals("purl", s1.getPrevious());
			assertFalse(s1.hasNext());
			assertEquals("title", s1.getTitle());
			assertFalse(s1.hasText());
		}
		catch(Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	public void testToJsonString() {
		StringBuilder sb = new StringBuilder();
		Strip s1 = new Strip("abc", "root", true, false, null, "ghi", "title", "-NA-");
		s1.toJsonString(sb);
		String expected = "{\"mHtmlUrl\":\"abc\", \"mImgFile\":\"" + s1.getImagePath() + "\", \"mRead\":\"true\", ";
		expected += "\"mFav\":\"false\", \"mNextUid\":\"ghi\", \"mTitle\":\"title\", \"mText\":\"-NA-\"}";
		assertEquals(expected, sb.toString());
	}

	public void testStripFetchFunctionalityNoHtml() {
		ComicParser p = new ComicParser() {
			@Override
			protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
				String date = url.replace("http://www.blondie.com/strip.php?comic=", "");
				String[] time = date.split("-");
				int year = Integer.parseInt(time[0]);
				int month = Integer.parseInt(time[1]);
				int day = Integer.parseInt(time[2]);
				String surl = String.format("http://content.comicskingdom.net/Blondie/Blondie.%4d%02d%02d.gif",year,month,day);
				strip.setTitle("Blondie: " + date);
				strip.setText("-NA-");
				return surl;
			}
			@Override
			protected boolean htmlNeeded() {
				return false;
			}
		};
		Strip s1 = new Strip("http://www.blondie.com/strip.php?comic=2012-01-01", "/sdcard");
		String path = null;
		try {
			path = s1.getImage(p);
			File f = new File(path);
			assertTrue(f.exists());
			assertTrue(s1.isRead());
			assertFalse(s1.hasText());
			Bitmap b = s1.getBitmapFromFile();
			assertNotNull(b);
			b.recycle();
			f.delete();
		}
		catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

	public void testStripFetchFunctionalityHtml() {
		ComicParser p = new ComicParser() {
			@Override
			protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
		    	String str;
				String final_str = null;
				String final_title = null;
				String final_itext = null;
				while ((str = reader.readLine()) != null) {
					int index1 = str.indexOf("img class=\"aligncenter\" src=");
					if (index1 != -1) {
						final_str = str;
						final_itext = str;
					}
					int index3 = str.indexOf("storytitle");
					if (index3 != -1) {
						final_title = str;
					}
				}
				final_str = final_str.replaceAll(".*src=\"","");
				final_str = final_str.replaceAll("\".*","");
				final_str = final_str.replaceAll(" ","%20");
				if(!final_str.contains("abstrusegoose.com")) {
					final_str = "http://www.abstrusegoose.com" + final_str;
		    	}
		    	final_title = final_title.replaceAll(".*bookmark\">","");
		    	final_title = final_title.replaceAll("<.*","");
				if(final_itext.contains("title")) {
					final_itext = final_itext.replaceAll(".*title=\"","");
					final_itext = final_itext.replaceAll("\".*","");
					if (final_itext.equals("")) {
						final_itext = "-NA-";
					}
				} else {
					final_itext = "-NA-";
		    	}
				strip.setTitle("AbstruseGoose: " + final_title);
				strip.setText(final_itext);
				return final_str;
			}
			@Override
			protected boolean htmlNeeded() {
				return true;
			}
		};
		Strip s1 = new Strip("http://www.abstrusegoose.com/6", "/sdcard");
		String path = null;
		try {
			path = s1.getImage(p);
			File f = new File(path);
			assertTrue(f.exists());
			assertTrue(s1.isRead());
			assertFalse(s1.hasText());
			Bitmap b = s1.getBitmapFromFile();
			assertNotNull(b);
			b.recycle();
			f.delete();
		}
		catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
		// this one has image text!
		Strip s2 = new Strip("http://www.abstrusegoose.com/200", "/sdcard");
		String path2 = null;
		try {
			path2 = s2.getImage(p);
			File f = new File(path2);
			assertTrue(f.exists());
			assertTrue(s2.isRead());
			assertTrue(s2.hasText());
			Bitmap b = s2.getBitmapFromFile();
			assertNotNull(b);
			b.recycle();
			f.delete();
		}
		catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

}
