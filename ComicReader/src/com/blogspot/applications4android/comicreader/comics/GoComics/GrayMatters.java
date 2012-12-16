package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class GrayMatters extends DailyGoComicsCom {
	public GrayMatters() {
		super();
		mComicName = "gray-matters";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 3, 2);
	}
}
