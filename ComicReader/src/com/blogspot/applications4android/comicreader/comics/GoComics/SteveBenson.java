   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class SteveBenson extends DailyGoComicsCom {
	public SteveBenson() {
		super();
		mComicName = "stevebenson";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2005, 0, 3);
	}
}
