package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class MythTickle extends DailyGoComicsCom {
	public MythTickle() {
		super();
		mComicName = "mythtickle";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 15);
	}
}
