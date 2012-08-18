   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TheFlyingMcCoys extends DailyGoComicsCom {
	public TheFlyingMcCoys() {
		super();
		mComicName = "theflyingmccoys";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2005, 4, 9);
	}
}
