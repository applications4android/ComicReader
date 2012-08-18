package com.blogspot.applications4android.comicreader.test.core;

import com.blogspot.applications4android.comicreader.core.ImageType;

import android.content.res.AssetManager;
import android.test.InstrumentationTestCase;

public class ImageTypeTest extends InstrumentationTestCase {

	public void testImageTypes() {
		try {
			AssetManager mgr = getInstrumentation().getContext().getAssets();
			assertEquals(ImageType.Types.BMP, ImageType.getImageType(mgr.open("ImageTypeTest/bmp.bmp")));
			assertEquals(ImageType.Types.JPG, ImageType.getImageType(mgr.open("ImageTypeTest/jpg.bmp")));
			assertEquals(ImageType.Types.GIF, ImageType.getImageType(mgr.open("ImageTypeTest/gif.gif")));
			assertEquals(ImageType.Types.JPG, ImageType.getImageType(mgr.open("ImageTypeTest/jpg.img")));
			assertEquals(ImageType.Types.JPG, ImageType.getImageType(mgr.open("ImageTypeTest/jpg.jpg")));
			assertEquals(ImageType.Types.PNG, ImageType.getImageType(mgr.open("ImageTypeTest/png.png")));
			assertEquals(ImageType.Types.UNKNOWN, ImageType.getImageType(mgr.open("ImageTypeTest/txt.txt")));
		}
		catch(Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}

}
