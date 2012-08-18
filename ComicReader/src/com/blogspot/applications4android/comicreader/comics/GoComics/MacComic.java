   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class MacComic extends DailyGoComicsCom {
	public MacComic() {
		super();
		mComicName = "mac";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 4, 20);
	}
}
