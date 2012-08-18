package com.blogspot.applications4android.comicreader.test.core;

import java.io.File;

import com.blogspot.applications4android.comicreader.core.ComicClassList;
import com.blogspot.applications4android.comicreader.core.FileUtils;

import android.content.res.AssetManager;
import android.test.InstrumentationTestCase;


public class ComicClassListTest extends InstrumentationTestCase {

	public void testGlobalVars() {
		assertEquals("classes.json", ComicClassList.CLASSES);
		assertEquals("selected.json", ComicClassList.SELECTED);
		assertEquals(1, ComicClassList.SORT_ALPHABETICAL);
		assertEquals(2, ComicClassList.SORT_IGNORE_ARTICLES);
		assertEquals(3, ComicClassList.SORT_EDITORIAL_LAST);
		assertEquals(4, ComicClassList.SORT_NEWEST_FIRST);
		assertEquals(5, ComicClassList.SORT_SELECTED_FIRST);
	}

	public void testComicClassList() {
		AssetManager mgr = getInstrumentation().getContext().getAssets();
		File f = new File(FileUtils.getSdcard(), "selected.json");
		f.delete();
		try {
			ComicClassList cc = new ComicClassList(mgr, "ComicClassListTest/classes.json", f);
			assertNotNull(cc);
			assertEquals(29, cc.length());
			assertNull(cc.getSelectedComicList());
			assertFalse(cc.isSelected(0));
			cc.toggleSelected(0);
			assertTrue(cc.isSelected(0));
			assertFalse(cc.selectedFile().exists());
			assertEquals(cc.selectedFile().getPath(), f.getPath());
			assertEquals("com.blogspot.applications4android.comicreader.comics", cc.getPackage());
			assertEquals(1, cc.numSelected());
			cc.sortClasses(ComicClassList.SORT_ALPHABETICAL);
			assertEquals(1, cc.getComicIndexFromTitle("A Girl and Her Fed"));
			assertEquals("A Girl and Her Fed", cc.getComicClassFromIndex(1).mName);
			cc.sortClasses(ComicClassList.SORT_IGNORE_ARTICLES);
			assertEquals(1, cc.getComicIndexFromTitle("Abstruse Goose"));
			cc.sortClasses(ComicClassList.SORT_EDITORIAL_LAST);
			assertEquals(28, cc.getComicIndexFromTitle("Editorial : Wit of the World"));
			cc.sortClasses(ComicClassList.SORT_NEWEST_FIRST);
			assertEquals(1, cc.getComicIndexFromTitle("Daddy's Home"));
			cc.sortClasses(ComicClassList.SORT_SELECTED_FIRST);
			assertEquals(1, cc.getComicIndexFromTitle("A Girl and Her Fed"));
			cc.setAllSelected(true);
			cc.storeSelected();
			assertTrue(cc.selectedFile().exists());
			assertEquals(cc.getSelectedComicList().length, 29);
			assertTrue(cc.isSelected(0));
		}
		catch(Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
		try {
			ComicClassList cc = new ComicClassList(mgr, "ComicClassListTest/classes.json", f);
			assertNotNull(cc);
			assertEquals(29, cc.length());
			assertEquals(cc.selectedFile().getPath(), f.getPath());
			assertTrue(f.exists());
			assertEquals(29, cc.numSelected());
			f.delete();
		}
		catch(Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
		f.delete();
	}

}
