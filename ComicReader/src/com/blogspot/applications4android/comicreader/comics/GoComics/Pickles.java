package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Pickles extends DailyGoComicsCom {
	public Pickles() {
		super();
		mComicName = "pickles";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2003, 0, 1);
	}
}
