package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Andertoons extends DailyGoComicsCom {
	public Andertoons() {
		super();
		mComicName = "andertoons";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 8, 1);
	}
}
