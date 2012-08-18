   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class RealLifeAdventures extends DailyGoComicsCom {
	public RealLifeAdventures() {
		super();
		mComicName = "reallifeadventures";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1998, 0, 1);
	}
}
