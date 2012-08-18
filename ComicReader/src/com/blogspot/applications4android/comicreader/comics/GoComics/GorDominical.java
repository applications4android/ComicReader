   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class GorDominical extends DailyGoComicsCom {
	public GorDominical() {
		super();
		mComicName = "espanol/gor-dominical";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 4, 22);
	}
}
