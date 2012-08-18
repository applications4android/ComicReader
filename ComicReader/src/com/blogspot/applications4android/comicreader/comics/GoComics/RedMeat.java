   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class RedMeat extends DailyGoComicsCom {
	public RedMeat() {
		super();
		mComicName = "redmeat";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2002, 2, 19);
	}
}
