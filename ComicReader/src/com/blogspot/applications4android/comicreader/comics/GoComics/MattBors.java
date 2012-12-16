package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class MattBors extends DailyGoComicsCom {
	public MattBors() {
		super();
		mComicName = "matt-bors";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 5, 1);
	}
}
