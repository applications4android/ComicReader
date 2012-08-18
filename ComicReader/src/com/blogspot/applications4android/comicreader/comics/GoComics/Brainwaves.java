   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Brainwaves extends DailyGoComicsCom {
	public Brainwaves() {
		super();
		mComicName = "brainwaves";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2005, 4, 16);
	}
}
