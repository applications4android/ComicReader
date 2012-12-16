package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class PreTeena extends DailyGoComicsCom {
	public PreTeena() {
		super();
		mComicName = "preteena";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2001, 3, 23);
	}
}
