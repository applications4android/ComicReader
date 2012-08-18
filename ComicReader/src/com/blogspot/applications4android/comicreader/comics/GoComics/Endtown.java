   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Endtown extends DailyGoComicsCom {
	public Endtown() {
		super();
		mComicName = "endtown";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2009, 0, 19);
	}
}
