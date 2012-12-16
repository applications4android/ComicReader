package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class DilbertClassics extends DailyGoComicsCom {
	public DilbertClassics() {
		super();
		mComicName = "dilbert-classics";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 5, 17);
	}
}
