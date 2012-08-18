   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class JackOhman extends DailyGoComicsCom {
	public JackOhman() {
		super();
		mComicName = "jackohman";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2001, 3, 20);
	}
}
