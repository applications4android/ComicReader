package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Geech extends DailyGoComicsCom {
	public Geech() {
		super();
		mComicName = "geech";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2000, 0, 1);
	}
}
