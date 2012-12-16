package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class CestlaVie extends DailyGoComicsCom {
	public CestlaVie() {
		super();
		mComicName = "cestlavie";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2003, 10, 11);
	}
}
