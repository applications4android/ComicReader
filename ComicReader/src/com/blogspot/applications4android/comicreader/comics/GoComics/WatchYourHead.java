   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class WatchYourHead extends DailyGoComicsCom {
	public WatchYourHead() {
		super();
		mComicName = "watchyourhead";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2006, 2, 27);
	}
}
