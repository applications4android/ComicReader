package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TomToles extends DailyGoComicsCom {
	public TomToles() {
		super();
		mComicName = "tomtoles";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1997, 10, 26);
	}
}
