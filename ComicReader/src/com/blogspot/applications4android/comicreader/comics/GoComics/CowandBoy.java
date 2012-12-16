package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class CowandBoy extends DailyGoComicsCom {
	public CowandBoy() {
		super();
		mComicName = "cowandboy";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2006, 0, 2);
	}
}
