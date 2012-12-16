package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class MarshallRamsey extends DailyGoComicsCom {
	public MarshallRamsey() {
		super();
		mComicName = "marshallramsey";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2008, 0, 7);
	}
}
