package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Nancy extends DailyGoComicsCom {
	public Nancy() {
		super();
		mComicName = "nancy";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 4, 23);
	}
}
