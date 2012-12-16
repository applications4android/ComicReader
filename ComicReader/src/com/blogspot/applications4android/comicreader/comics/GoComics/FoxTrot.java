package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class FoxTrot extends DailyGoComicsCom {
	public FoxTrot() {
		super();
		mComicName = "foxtrot";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1996, 2, 11);
	}
}
