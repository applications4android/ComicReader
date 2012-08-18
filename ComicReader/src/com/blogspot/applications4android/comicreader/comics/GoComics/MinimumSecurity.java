   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class MinimumSecurity extends DailyGoComicsCom {
	public MinimumSecurity() {
		super();
		mComicName = "minimumsecurity";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2001, 8, 3);
	}
}
