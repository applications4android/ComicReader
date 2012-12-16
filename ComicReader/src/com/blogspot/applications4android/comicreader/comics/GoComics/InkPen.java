package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class InkPen extends DailyGoComicsCom {
	public InkPen() {
		super();
		mComicName = "inkpen";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2005, 10, 7);
	}
}
