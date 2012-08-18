   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class ChanLowe extends DailyGoComicsCom {
	public ChanLowe() {
		super();
		mComicName = "chanlowe";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2001, 3, 23);
	}
}
