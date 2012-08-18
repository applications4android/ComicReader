package com.blogspot.applications4android.comicreader.test.core;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.blogspot.applications4android.comicreader.core.JsonUtils;

import android.content.res.AssetManager;
import android.test.InstrumentationTestCase;


public class JsonUtilsTest extends InstrumentationTestCase {
	private AssetManager mMgr;
	private String mGood = "JsonUtilsTest/classes.json";

	@Override
	public void setUp() {
		mMgr = getInstrumentation().getContext().getAssets();
	}

	public void testSettingsRoot() {
		// mGood file
		try {
			JSONObject root = JsonUtils.settingsRoot(mGood, mMgr);
			assertNotNull(root);
			assertTrue(root.has("package"));
			assertTrue(root.has("classes"));
			assertEquals("com.blogspot.applications4android.comicreader.comics", root.getString("package"));
		}
		catch(Exception e) {
			fail(e.toString());
		}
		// bad file
		try {
			JsonUtils.settingsRoot("JsonUtilsTest/bad.json", mMgr);
			fail("'bad.json' parsing should fail!");
		}
		catch(Exception e) {
			assertTrue(true);
		}
	}

	public void testJsonRoot() {
		// mGood file
		try {
			JSONObject root = JsonUtils.jsonRoot(mMgr.open(mGood));
			assertNotNull(root);
			assertTrue(root.has("package"));
			assertTrue(root.has("classes"));
			assertEquals("com.blogspot.applications4android.comicreader.comics", root.getString("package"));
		}
		catch(Exception e) {
			fail(e.toString());
		}
	}

	public void testListOfComics() {
		try {
			JSONArray classes = JsonUtils.listOfComics(mGood, mMgr, "classes");
			assertNotNull(classes);
			assertEquals(107, classes.length());
		}
		catch(Exception e) {
			fail(e.toString());
		}
	}

	public void testListOfAllComicClasses() {
		try {
			String[] all = JsonUtils.listOfAllComicClasses(mGood, mMgr, "classes");
			assertNotNull(all);
			assertEquals(107, all.length);
		}
		catch(Exception e) {
			fail(e.toString());
		}
	}

	public void testListOfAllComicNames() {
		try {
			String[] all = JsonUtils.listOfAllComicNames(mGood, mMgr, "classes");
			assertNotNull(all);
			assertEquals(107, all.length);
		}
		catch(Exception e) {
			fail(e.toString());
		}
	}

	public void testListOfAllNewComicIndices() {
		try {
			ArrayList<Integer> all = JsonUtils.listOfAllNewComicIndices(mGood, mMgr, "classes");
			assertEquals(9, all.size());
		}
		catch(Exception e) {
			fail(e.toString());
		}
	}

	public void testListOfAllComicPrefKeys() {
		try {
			String[] all = JsonUtils.listOfAllComicPrefKeys(mGood, mMgr, "classes");
			assertEquals(107, all.length);
		}
		catch(Exception e) {
			fail(e.toString());
		}
	}

}
