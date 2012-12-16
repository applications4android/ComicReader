package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class ViewsAfrica extends DailyGoComicsCom {
	public ViewsAfrica() {
		super();
		mComicName = "viewsafrica";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 1, 3);
	}
}
