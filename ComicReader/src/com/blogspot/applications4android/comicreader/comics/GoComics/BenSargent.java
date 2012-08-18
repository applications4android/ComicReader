   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class BenSargent extends DailyGoComicsCom {
	public BenSargent() {
		super();
		mComicName = "bensargent";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1999, 6, 1);
	}
}
