package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Badlands extends DailyGoComicsCom {
	public Badlands() {
		super();
		mComicName = "badlands";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 10, 20);
	}
}
