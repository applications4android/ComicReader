   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Ferdnand extends DailyGoComicsCom {
	public Ferdnand() {
		super();
		mComicName = "ferdnand";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 4, 31);
	}
}
