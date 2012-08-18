   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class JimMorin extends DailyGoComicsCom {
	public JimMorin() {
		super();
		mComicName = "jimmorin";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2005, 11, 12);
	}
}
