   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TheOtherCoast extends DailyGoComicsCom {
	public TheOtherCoast() {
		super();
		mComicName = "theothercoast";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2002, 0, 1);
	}
}
