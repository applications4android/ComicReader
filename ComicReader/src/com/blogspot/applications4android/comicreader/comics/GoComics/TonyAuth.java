   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TonyAuth extends DailyGoComicsCom {
	public TonyAuth() {
		super();
		mComicName = "tonyauth";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1996, 3, 5);
	}
}
