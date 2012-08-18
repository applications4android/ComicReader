   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class BigTop extends DailyGoComicsCom {
	public BigTop() {
		super();
		mComicName = "bigtop";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2001, 3, 22);
	}
}
