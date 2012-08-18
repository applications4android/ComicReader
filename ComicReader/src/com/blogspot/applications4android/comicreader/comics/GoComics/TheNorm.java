   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TheNorm extends DailyGoComicsCom {
	public TheNorm() {
		super();
		mComicName = "thenorm";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2006, 11, 10);
	}
}
