package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class JoeVanilla extends DailyGoComicsCom {
	public JoeVanilla() {
		super();
		mComicName = "joevanilla";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2009, 1, 16);
	}
}
