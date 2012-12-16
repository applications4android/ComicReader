package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class AlleyOop extends DailyGoComicsCom {
	public AlleyOop() {
		super();
		mComicName = "alley-oop";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1996, 0, 1);
	}
}
