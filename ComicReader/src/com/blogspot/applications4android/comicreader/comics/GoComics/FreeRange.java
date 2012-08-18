   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class FreeRange extends DailyGoComicsCom {
	public FreeRange() {
		super();
		mComicName = "freerange";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 1, 3);
	}
}
