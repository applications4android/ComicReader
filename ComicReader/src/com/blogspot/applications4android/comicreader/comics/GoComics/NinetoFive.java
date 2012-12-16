package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class NinetoFive extends DailyGoComicsCom {
	public NinetoFive() {
		super();
		mComicName = "9to5";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2001, 3, 13);
	}
}
