package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class HomeandAway extends DailyGoComicsCom {
	public HomeandAway() {
		super();
		mComicName = "homeandaway";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2008, 5, 23);
	}
}
