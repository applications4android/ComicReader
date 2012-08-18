   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class WTDuck extends DailyGoComicsCom {
	public WTDuck() {
		super();
		mComicName = "wtduck";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2009, 0, 5);
	}
}
