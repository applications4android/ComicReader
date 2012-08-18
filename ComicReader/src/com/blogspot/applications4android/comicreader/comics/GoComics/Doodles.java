   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Doodles extends DailyGoComicsCom {
	public Doodles() {
		super();
		mComicName = "doodles";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2001, 3, 8);
	}
}
