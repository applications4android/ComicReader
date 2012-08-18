   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class DickLocher extends DailyGoComicsCom {
	public DickLocher() {
		super();
		mComicName = "dicklocher";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2001, 3, 20);
	}
}
