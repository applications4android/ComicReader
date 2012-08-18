   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class SmallWorld extends DailyGoComicsCom {
	public SmallWorld() {
		super();
		mComicName = "smallworld";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2003, 3, 20);
	}
}
