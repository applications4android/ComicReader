package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class MotleyClassics extends DailyGoComicsCom {
	public MotleyClassics() {
		super();
		mComicName = "motley-classics";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 5, 12);
	}
}
