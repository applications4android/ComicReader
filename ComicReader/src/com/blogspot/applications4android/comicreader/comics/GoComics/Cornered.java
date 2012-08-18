   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Cornered extends DailyGoComicsCom {
	public Cornered() {
		super();
		mComicName = "cornered";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1997, 8, 1);
	}
}
