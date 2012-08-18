   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class RudyPark extends DailyGoComicsCom {
	public RudyPark() {
		super();
		mComicName = "rudypark";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 4, 16);
	}
}
