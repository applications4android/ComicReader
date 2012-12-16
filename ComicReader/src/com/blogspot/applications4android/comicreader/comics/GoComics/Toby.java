package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TOBY extends DailyGoComicsCom {
	public TOBY() {
		super();
		mComicName = "toby";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2008, 1, 11);
	}
}
