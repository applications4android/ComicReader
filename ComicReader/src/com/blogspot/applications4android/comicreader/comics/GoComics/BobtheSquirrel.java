   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class BobtheSquirrel extends DailyGoComicsCom {
	public BobtheSquirrel() {
		super();
		mComicName = "bobthesquirrel";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2004, 0, 1);
	}
}
