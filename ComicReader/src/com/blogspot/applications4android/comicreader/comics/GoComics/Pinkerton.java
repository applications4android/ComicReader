package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Pinkerton extends DailyGoComicsCom {
	public Pinkerton() {
		super();
		mComicName = "pinkerton";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 9, 15);
	}
}
