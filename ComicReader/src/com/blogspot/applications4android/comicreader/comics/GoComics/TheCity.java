   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TheCity extends DailyGoComicsCom {
	public TheCity() {
		super();
		mComicName = "thecity";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2003, 2, 5);
	}
}
