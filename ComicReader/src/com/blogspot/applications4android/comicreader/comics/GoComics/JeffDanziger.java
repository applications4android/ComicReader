package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class JeffDanziger extends DailyGoComicsCom {
	public JeffDanziger() {
		super();
		mComicName = "jeffdanziger";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2000, 7, 14);
	}
}
