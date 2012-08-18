   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Garfield extends DailyGoComicsCom {
	public Garfield() {
		super();
		mComicName = "garfield";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1978, 5, 19);
	}
}
