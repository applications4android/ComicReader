package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class JerryHolbert extends DailyGoComicsCom {
	public JerryHolbert() {
		super();
		mComicName = "jerryholbert";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 5, 1);
	}
}
