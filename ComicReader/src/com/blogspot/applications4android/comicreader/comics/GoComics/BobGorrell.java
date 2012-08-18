   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class BobGorrell extends DailyGoComicsCom {
	public BobGorrell() {
		super();
		mComicName = "bobgorrell";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2004, 2, 25);
	}
}
