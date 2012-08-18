   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class ThinLines extends DailyGoComicsCom {
	public ThinLines() {
		super();
		mComicName = "thinlines";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2010, 4, 10);
	}
}
