   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class HeartoftheCity extends DailyGoComicsCom {
	public HeartoftheCity() {
		super();
		mComicName = "heartofthecity";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1999, 6, 1);
	}
}
