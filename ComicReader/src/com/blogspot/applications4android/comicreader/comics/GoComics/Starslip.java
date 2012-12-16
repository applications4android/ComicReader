package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Starslip extends DailyGoComicsCom {
	public Starslip() {
		super();
		mComicName = "starslip";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 6, 6);
	}
}
