package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class JanesWorld extends DailyGoComicsCom {
	public JanesWorld() {
		super();
		mComicName = "janesworld";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 3, 11);
	}
}
