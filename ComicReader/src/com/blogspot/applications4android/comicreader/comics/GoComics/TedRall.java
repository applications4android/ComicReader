package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TedRall extends DailyGoComicsCom {
	public TedRall() {
		super();
		mComicName = "tedrall";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1996, 10, 5);
	}
}
