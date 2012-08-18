   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class ThatsLife extends DailyGoComicsCom {
	public ThatsLife() {
		super();
		mComicName = "thats-life";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2000, 0, 1);
	}
}
