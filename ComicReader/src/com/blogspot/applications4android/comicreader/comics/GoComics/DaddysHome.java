package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class DaddysHome extends DailyGoComicsCom {
	public DaddysHome() {
		super();
		mComicName = "daddyshome";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2008, 2, 23);
	}
}
