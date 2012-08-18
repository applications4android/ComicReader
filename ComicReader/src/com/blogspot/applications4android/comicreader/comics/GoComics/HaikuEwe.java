   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class HaikuEwe extends DailyGoComicsCom {
	public HaikuEwe() {
		super();
		mComicName = "haikuewe";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2009, 2, 9);
	}
}
