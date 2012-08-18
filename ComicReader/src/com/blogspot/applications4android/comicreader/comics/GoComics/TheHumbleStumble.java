   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TheHumbleStumble extends DailyGoComicsCom {
	public TheHumbleStumble() {
		super();
		mComicName = "humble-stumble";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2005, 7, 15);
	}
}
