   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Beardo extends DailyGoComicsCom {
	public Beardo() {
		super();
		mComicName = "beardo";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2009, 11, 1);
	}
}
