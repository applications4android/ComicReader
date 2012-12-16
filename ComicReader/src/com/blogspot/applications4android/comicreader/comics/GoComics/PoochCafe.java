package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class PoochCafe extends DailyGoComicsCom {
	public PoochCafe() {
		super();
		mComicName = "poochcafe";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2003, 3, 27);
	}
}
