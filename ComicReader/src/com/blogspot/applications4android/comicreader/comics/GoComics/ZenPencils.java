package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class ZenPencils extends DailyGoComicsCom {
	public ZenPencils() {
		super();
		mComicName = "zen-pencils";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2013, Calendar.JULY, 9);
	}
}
