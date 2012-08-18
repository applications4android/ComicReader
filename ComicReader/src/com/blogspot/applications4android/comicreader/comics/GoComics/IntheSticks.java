   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class IntheSticks extends DailyGoComicsCom {
	public IntheSticks() {
		super();
		mComicName = "inthesticks";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2009, 9, 11);
	}
}
