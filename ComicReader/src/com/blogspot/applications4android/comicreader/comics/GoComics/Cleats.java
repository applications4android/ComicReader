   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Cleats extends DailyGoComicsCom {
	public Cleats() {
		super();
		mComicName = "cleats";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2001, 0, 1);
	}
}
