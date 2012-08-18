   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class IntheBleachers extends DailyGoComicsCom {
	public IntheBleachers() {
		super();
		mComicName = "inthebleachers";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1996, 7, 12);
	}
}
