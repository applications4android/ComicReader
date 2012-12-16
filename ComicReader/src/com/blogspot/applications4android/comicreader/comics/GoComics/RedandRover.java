package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class RedandRover extends DailyGoComicsCom {
	public RedandRover() {
		super();
		mComicName = "redandrover";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2003, 0, 1);
	}
}
