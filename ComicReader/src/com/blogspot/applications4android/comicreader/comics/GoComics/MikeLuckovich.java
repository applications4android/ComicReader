   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class MikeLuckovich extends DailyGoComicsCom {
	public MikeLuckovich() {
		super();
		mComicName = "mikeluckovich";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2002, 7, 6);
	}
}
