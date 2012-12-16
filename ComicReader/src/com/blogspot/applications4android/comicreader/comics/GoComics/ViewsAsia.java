package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class ViewsAsia extends DailyGoComicsCom {
	public ViewsAsia() {
		super();
		mComicName = "viewsasia";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 0, 15);
	}
}
