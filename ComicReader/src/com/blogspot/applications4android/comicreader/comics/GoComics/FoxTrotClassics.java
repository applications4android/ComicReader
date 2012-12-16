package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class FoxTrotClassics extends DailyGoComicsCom {
	public FoxTrotClassics() {
		super();
		mComicName = "foxtrotclassics";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 0, 1);
	}
}
