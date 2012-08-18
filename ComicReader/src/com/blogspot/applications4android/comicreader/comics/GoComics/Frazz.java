   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Frazz extends DailyGoComicsCom {
	public Frazz() {
		super();
		mComicName = "frazz";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2001, 3, 2);
	}
}
