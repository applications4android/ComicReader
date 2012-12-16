package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TheDoozies extends DailyGoComicsCom {
	public TheDoozies() {
		super();
		mComicName = "thedoozies";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2008, 11, 1);
	}
}
