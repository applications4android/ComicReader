package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class HamShears extends DailyGoComicsCom {
	public HamShears() {
		super();
		mComicName = "ham-shears";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 10, 7);
	}
}
