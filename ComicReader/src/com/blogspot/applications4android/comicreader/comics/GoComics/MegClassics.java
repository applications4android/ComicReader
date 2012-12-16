package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class MegClassics extends DailyGoComicsCom {
	public MegClassics() {
		super();
		mComicName = "meg-classics";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1997, 2, 3);
	}
}
