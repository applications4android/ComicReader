   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Lio extends DailyGoComicsCom {
	public Lio() {
		super();
		mComicName = "lio";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2006, 4, 15);
	}
}
