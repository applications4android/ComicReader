   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Neurotica extends DailyGoComicsCom {
	public Neurotica() {
		super();
		mComicName = "neurotica";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2003, 11, 21);
	}
}
