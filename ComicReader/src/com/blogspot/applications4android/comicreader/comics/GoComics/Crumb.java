package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Crumb extends DailyGoComicsCom {
	public Crumb() {
		super();
		mComicName = "crumb";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2008, 11, 24);
	}
}
