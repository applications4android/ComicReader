package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class JoeHeller extends DailyGoComicsCom {
	public JoeHeller() {
		super();
		mComicName = "joe-heller";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 1, 15);
	}
}
