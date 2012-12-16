package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class FrogApplause extends DailyGoComicsCom {
	public FrogApplause() {
		super();
		mComicName = "frogapplause";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2006, 11, 20);
	}
}
