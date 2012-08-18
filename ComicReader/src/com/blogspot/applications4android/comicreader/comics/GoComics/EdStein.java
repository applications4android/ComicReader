   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class EdStein extends DailyGoComicsCom {
	public EdStein() {
		super();
		mComicName = "ed-stein";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 5, 2);
	}
}
