package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class BloomCounty extends DailyGoComicsCom {
	public BloomCounty() {
		super();
		mComicName = "bloomcounty";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1980, 11, 4);
	}
}
