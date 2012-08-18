   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class MagicinaMinute extends DailyGoComicsCom {
	public MagicinaMinute() {
		super();
		mComicName = "magicinaminute";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 7, 7);
	}
}
