   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Pluggers extends DailyGoComicsCom {
	public Pluggers() {
		super();
		mComicName = "pluggers";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2001, 3, 8);
	}
}
