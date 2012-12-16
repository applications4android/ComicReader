package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class FreshlySqueezed extends DailyGoComicsCom {
	public FreshlySqueezed() {
		super();
		mComicName = "freshlysqueezed";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 4, 26);
	}
}
