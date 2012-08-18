   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class SteveSack extends DailyGoComicsCom {
	public SteveSack() {
		super();
		mComicName = "stevesack";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2001, 3, 19);
	}
}
