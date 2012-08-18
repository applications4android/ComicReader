   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class KSquaredComics extends DailyGoComicsCom {
	public KSquaredComics() {
		super();
		mComicName = "k-squared-comics";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 2, 20);
	}
}
