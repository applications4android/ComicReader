package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class GlennMcCoy extends DailyGoComicsCom {
	public GlennMcCoy() {
		super();
		mComicName = "glennmccoy";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1999, 7, 25);
	}
}
