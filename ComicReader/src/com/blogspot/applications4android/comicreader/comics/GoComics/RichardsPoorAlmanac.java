package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class RichardsPoorAlmanac extends DailyGoComicsCom {
	public RichardsPoorAlmanac() {
		super();
		mComicName = "richards-poor-almanac";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2010, 11, 16);
	}
}
