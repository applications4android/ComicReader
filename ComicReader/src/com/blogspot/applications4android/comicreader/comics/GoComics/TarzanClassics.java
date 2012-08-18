   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TarzanClassics extends DailyGoComicsCom {
	public TarzanClassics() {
		super();
		mComicName = "tarzan";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2002, 0, 6);
	}
}
