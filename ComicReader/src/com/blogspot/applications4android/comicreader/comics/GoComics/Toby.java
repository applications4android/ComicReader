package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Toby extends DailyGoComicsCom {
	public Toby() {
		super();
		mComicName = "toby";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2008, 1, 11);
	}
}
