   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class AndNow extends DailyGoComicsCom {
	public AndNow() {
		super();
		mComicName = "and-now";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 4, 31);
	}
}
