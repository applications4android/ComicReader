package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Biographic extends DailyGoComicsCom {
	public Biographic() {
		super();
		mComicName = "biographic";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2005, 7, 14);
	}
}
