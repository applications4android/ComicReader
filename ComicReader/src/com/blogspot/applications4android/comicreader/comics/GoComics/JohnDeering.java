   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class JohnDeering extends DailyGoComicsCom {
	public JohnDeering() {
		super();
		mComicName = "johndeering";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2005, 0, 4);
	}
}
