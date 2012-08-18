   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Herman extends DailyGoComicsCom {
	public Herman() {
		super();
		mComicName = "herman";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 4, 16);
	}
}
