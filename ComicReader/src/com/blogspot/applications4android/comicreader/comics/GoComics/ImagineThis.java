package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class ImagineThis extends DailyGoComicsCom {
	public ImagineThis() {
		super();
		mComicName = "imaginethis";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2009, 3, 8);
	}
}
