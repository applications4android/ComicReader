   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Agnes extends DailyGoComicsCom {
	public Agnes() {
		super();
		mComicName = "agnes";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2002, 0, 1);
	}
}
