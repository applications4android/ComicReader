package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class ClayJones extends DailyGoComicsCom {
	public ClayJones() {
		super();
		mComicName = "clayjones";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2005, 0, 3);
	}
}
