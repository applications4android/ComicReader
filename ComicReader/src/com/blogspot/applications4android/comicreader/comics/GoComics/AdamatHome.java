   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class AdamatHome extends DailyGoComicsCom {
	public AdamatHome() {
		super();
		mComicName = "adamathome";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1995, 5, 20);
	}
}
