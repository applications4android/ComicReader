package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class ViivinWagner extends DailyGoComicsCom {
	public ViivinWagner() {
		super();
		mComicName = "viivi-and-wagner";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 5, 29);
	}
}
