package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class WitoftheWorld extends DailyGoComicsCom {
	public WitoftheWorld() {
		super();
		mComicName = "witoftheworld";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2006, 11, 24);
	}
}
