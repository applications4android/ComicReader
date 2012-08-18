   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class NickAnderson extends DailyGoComicsCom {
	public NickAnderson() {
		super();
		mComicName = "nickanderson";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2003, 0, 9);
	}
}
