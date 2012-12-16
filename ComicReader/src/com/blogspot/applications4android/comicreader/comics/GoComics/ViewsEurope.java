package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class ViewsEurope extends DailyGoComicsCom {
	public ViewsEurope() {
		super();
		mComicName = "viewseurope";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 0, 19);
	}
}
