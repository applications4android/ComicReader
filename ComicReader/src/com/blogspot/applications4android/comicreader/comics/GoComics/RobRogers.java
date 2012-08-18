   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class RobRogers extends DailyGoComicsCom {
	public RobRogers() {
		super();
		mComicName = "robrogers";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 4, 26);
	}
}
