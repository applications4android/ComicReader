   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class BillDay extends DailyGoComicsCom {
	public BillDay() {
		super();
		mComicName = "billday";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 5, 1);
	}
}
