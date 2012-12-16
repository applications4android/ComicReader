package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class BigNate extends DailyGoComicsCom {
	public BigNate() {
		super();
		mComicName = "bignate";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1997, 0, 1);
	}
}
