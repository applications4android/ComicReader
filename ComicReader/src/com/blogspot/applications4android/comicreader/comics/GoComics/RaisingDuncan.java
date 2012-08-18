   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class RaisingDuncan extends DailyGoComicsCom {
	public RaisingDuncan() {
		super();
		mComicName = "raising-duncan";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2000, 6, 17);
	}
}
