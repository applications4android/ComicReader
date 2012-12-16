package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class PCandPixel extends DailyGoComicsCom {
	public PCandPixel() {
		super();
		mComicName = "pcandpixel";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 4, 31);
	}
}
