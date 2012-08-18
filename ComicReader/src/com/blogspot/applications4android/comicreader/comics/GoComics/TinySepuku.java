   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TinySepuku extends DailyGoComicsCom {
	public TinySepuku() {
		super();
		mComicName = "tinysepuku";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2003, 3, 3);
	}
}
