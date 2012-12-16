package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Kliban extends DailyGoComicsCom {
	public Kliban() {
		super();
		mComicName = "kliban";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 2, 18);
	}
}
