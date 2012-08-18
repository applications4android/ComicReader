   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class GilThorp extends DailyGoComicsCom {
	public GilThorp() {
		super();
		mComicName = "gilthorp";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2001, 3, 18);
	}
}
