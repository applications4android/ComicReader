package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class SarahScribbles extends DailyGoComicsCom {
	public SarahScribbles() {
		super();
		mComicName = "sarahs-scribbles";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2014, Calendar.JANUARY, 2);
	}
}
