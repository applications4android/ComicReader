   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class CalvinandHobbes extends DailyGoComicsCom {
	public CalvinandHobbes() {
		super();
		mComicName = "calvinandhobbes";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1985, 10, 17);
	}
}
