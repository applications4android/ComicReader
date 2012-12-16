package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class ChuckAsay extends DailyGoComicsCom {
	public ChuckAsay() {
		super();
		mComicName = "chuckasay";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2002, 7, 4);
	}
}
