   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class BruceBeattie extends DailyGoComicsCom {
	public BruceBeattie() {
		super();
		mComicName = "brucebeattie";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2008, 6, 31);
	}
}
