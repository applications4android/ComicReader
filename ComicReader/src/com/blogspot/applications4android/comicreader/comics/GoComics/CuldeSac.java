package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class CuldeSac extends DailyGoComicsCom {
	public CuldeSac() {
		super();
		mComicName = "culdesac";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 8, 10);
	}
}
