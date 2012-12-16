package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class DanWasserman extends DailyGoComicsCom {
	public DanWasserman() {
		super();
		mComicName = "danwasserman";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2001, 3, 20);
	}
}
