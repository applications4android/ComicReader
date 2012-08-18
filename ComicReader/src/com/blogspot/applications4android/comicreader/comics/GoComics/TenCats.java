   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TenCats extends DailyGoComicsCom {
	public TenCats() {
		super();
		mComicName = "ten-cats";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 0, 23);
	}
}
