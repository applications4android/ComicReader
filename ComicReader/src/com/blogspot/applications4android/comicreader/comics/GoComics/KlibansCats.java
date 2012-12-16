package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class KlibansCats extends DailyGoComicsCom {
	public KlibansCats() {
		super();
		mComicName = "klibans-cats";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 2, 27);
	}
}
