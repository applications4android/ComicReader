   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class HerbandJamaal extends DailyGoComicsCom {
	public HerbandJamaal() {
		super();
		mComicName = "herbandjamaal";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2002, 0, 1);
	}
}
