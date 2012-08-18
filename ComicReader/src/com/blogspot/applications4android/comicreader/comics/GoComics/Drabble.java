   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Drabble extends DailyGoComicsCom {
	public Drabble() {
		super();
		mComicName = "drabble";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2005, 0, 2);
	}
}
