   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TheBoondocks extends DailyGoComicsCom {
	public TheBoondocks() {
		super();
		mComicName = "boondocks";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1999, 3, 19);
	}
}
