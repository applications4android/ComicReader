package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class MediumLarge extends DailyGoComicsCom {
	public MediumLarge() {
		super();
		mComicName = "medium-large";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 8, 18);
	}
}
