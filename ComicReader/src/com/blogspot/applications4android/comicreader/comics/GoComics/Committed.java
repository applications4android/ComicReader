package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Committed extends DailyGoComicsCom {
	public Committed() {
		super();
		mComicName = "committed";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 5, 8);
	}
}
