package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class PricklyCity extends DailyGoComicsCom {
	public PricklyCity() {
		super();
		mComicName = "pricklycity";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2004, 6, 11);
	}
}
