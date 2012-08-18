   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Slowpoke extends DailyGoComicsCom {
	public Slowpoke() {
		super();
		mComicName = "slowpoke";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2002, 6, 22);
	}
}
