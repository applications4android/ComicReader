package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class FatCats extends DailyGoComicsCom {
	public FatCats() {
		super();
		mComicName = "fat-cats";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 5, 29);
	}
}
