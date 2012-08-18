   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Bewley extends DailyGoComicsCom {
	public Bewley() {
		super();
		mComicName = "bewley";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2009, 10, 9);
	}
}
