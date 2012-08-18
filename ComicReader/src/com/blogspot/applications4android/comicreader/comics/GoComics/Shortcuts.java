   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Shortcuts extends DailyGoComicsCom {
	public Shortcuts() {
		super();
		mComicName = "shortcuts";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 5, 5);
	}
}
