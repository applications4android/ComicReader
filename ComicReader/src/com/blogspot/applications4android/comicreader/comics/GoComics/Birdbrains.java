   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Birdbrains extends DailyGoComicsCom {
	public Birdbrains() {
		super();
		mComicName = "birdbrains";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 0, 1);
	}
}
