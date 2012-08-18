   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class LibertyMeadows extends DailyGoComicsCom {
	public LibertyMeadows() {
		super();
		mComicName = "libertymeadows";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2002, 0, 1);
	}
}
