package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class MariasDay extends DailyGoComicsCom {
	public MariasDay() {
		super();
		mComicName = "marias-day";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 6, 11);
	}
}
