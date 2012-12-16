package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class ChuckleBros extends DailyGoComicsCom {
	public ChuckleBros() {
		super();
		mComicName = "chucklebros";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2009, 1, 2);
	}
}
