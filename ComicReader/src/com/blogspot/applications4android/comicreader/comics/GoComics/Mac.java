package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Mac extends DailyGoComicsCom {
	public Mac() {
		super();
		mComicName = "mac";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 4, 20);
	}
}
