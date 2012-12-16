package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class NothingisNotSomething extends DailyGoComicsCom {
	public NothingisNotSomething() {
		super();
		mComicName = "nothing-is-not-something";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 10, 5);
	}
}
