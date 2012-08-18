   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class BreakofDay extends DailyGoComicsCom {
	public BreakofDay() {
		super();
		mComicName = "break-of-day";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 7, 29);
	}
}
