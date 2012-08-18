   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class BoNanas extends DailyGoComicsCom {
	public BoNanas() {
		super();
		mComicName = "bonanas";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2004, 0, 1);
	}
}
