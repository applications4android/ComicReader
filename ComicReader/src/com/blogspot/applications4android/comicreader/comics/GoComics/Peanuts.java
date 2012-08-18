   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Peanuts extends DailyGoComicsCom {
	public Peanuts() {
		super();
		mComicName = "peanuts";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1950, 0, 5);
	}
}
