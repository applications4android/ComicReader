   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class GingerMeggs extends DailyGoComicsCom {
	public GingerMeggs() {
		super();
		mComicName = "gingermeggs";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2004, 3, 1);
	}
}
