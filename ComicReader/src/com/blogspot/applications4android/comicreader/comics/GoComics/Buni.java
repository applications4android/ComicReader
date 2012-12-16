package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Buni extends DailyGoComicsCom {
	public Buni() {
		super();
		mComicName = "buni";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 6, 6);
	}
}
