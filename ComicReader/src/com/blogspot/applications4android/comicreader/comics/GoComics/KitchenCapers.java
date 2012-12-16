package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class KitchenCapers extends DailyGoComicsCom {
	public KitchenCapers() {
		super();
		mComicName = "kitchen-capers";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 4, 7);
	}
}
