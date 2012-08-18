   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class SigneWilkinson extends DailyGoComicsCom {
	public SigneWilkinson() {
		super();
		mComicName = "signewilkinson";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2003, 1, 24);
	}
}
