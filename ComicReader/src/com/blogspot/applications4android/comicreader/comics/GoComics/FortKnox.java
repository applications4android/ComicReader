   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class FortKnox extends DailyGoComicsCom {
	public FortKnox() {
		super();
		mComicName = "fortknox";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2009, 9, 5);
	}
}
