   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TheKChronicles extends DailyGoComicsCom {
	public TheKChronicles() {
		super();
		mComicName = "thekchronicles";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2003, 6, 1);
	}
}
