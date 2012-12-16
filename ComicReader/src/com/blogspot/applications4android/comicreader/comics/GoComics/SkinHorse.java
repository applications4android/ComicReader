package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class SkinHorse extends DailyGoComicsCom {
	public SkinHorse() {
		super();
		mComicName = "skinhorse";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 11, 31);
	}
}
