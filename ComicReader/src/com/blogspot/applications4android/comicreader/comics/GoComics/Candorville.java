package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Candorville extends DailyGoComicsCom {
	public Candorville() {
		super();
		mComicName = "candorville";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2004, 0, 1);
	}
}
