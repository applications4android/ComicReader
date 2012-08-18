   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Luann extends DailyGoComicsCom {
	public Luann() {
		super();
		mComicName = "luann";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1985, 2, 17);
	}
}
