package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Computoon extends DailyGoComicsCom {
	public Computoon() {
		super();
		mComicName = "compu-toon";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2001, 3, 23);
	}
}
