package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class McArroni extends DailyGoComicsCom {
	public McArroni() {
		super();
		mComicName = "mcarroni";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 9, 24);
	}
}
