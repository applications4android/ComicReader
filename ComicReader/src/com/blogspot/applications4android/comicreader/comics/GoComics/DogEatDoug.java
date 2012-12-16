package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class DogEatDoug extends DailyGoComicsCom {
	public DogEatDoug() {
		super();
		mComicName = "dogeatdoug";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2005, 0, 2);
	}
}
