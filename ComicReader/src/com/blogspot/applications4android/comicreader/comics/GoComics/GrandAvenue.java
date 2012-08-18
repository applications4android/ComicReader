   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class GrandAvenue extends DailyGoComicsCom {
	public GrandAvenue() {
		super();
		mComicName = "grand-avenue";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 4, 23);
	}
}
