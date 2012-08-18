   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class DonWright extends DailyGoComicsCom {
	public DonWright() {
		super();
		mComicName = "donwright";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2001, 3, 2);
	}
}
