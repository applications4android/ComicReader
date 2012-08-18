   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class BarkingCrayon extends DailyGoComicsCom {
	public BarkingCrayon() {
		super();
		mComicName = "barking-crayon";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 4, 1);
	}
}
