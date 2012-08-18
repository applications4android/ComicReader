   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Farcus extends DailyGoComicsCom {
	public Farcus() {
		super();
		mComicName = "farcus";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 5, 15);
	}
}
