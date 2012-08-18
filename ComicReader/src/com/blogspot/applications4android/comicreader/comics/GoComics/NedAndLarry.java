   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class NedAndLarry extends DailyGoComicsCom {
	public NedAndLarry() {
		super();
		mComicName = "ned-and-larry";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 4, 23);
	}
}
