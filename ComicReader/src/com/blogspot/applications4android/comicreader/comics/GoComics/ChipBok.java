   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class ChipBok extends DailyGoComicsCom {
	public ChipBok() {
		super();
		mComicName = "chipbok";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2002, 7, 9);
	}
}
