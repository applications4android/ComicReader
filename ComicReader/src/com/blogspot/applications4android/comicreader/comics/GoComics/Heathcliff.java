   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Heathcliff extends DailyGoComicsCom {
	public Heathcliff() {
		super();
		mComicName = "heathcliff";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2002, 0, 1);
	}
}
