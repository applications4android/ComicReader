package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class JeffStahler extends DailyGoComicsCom {
	public JeffStahler() {
		super();
		mComicName = "jeffstahler";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 5, 1);
	}
}
