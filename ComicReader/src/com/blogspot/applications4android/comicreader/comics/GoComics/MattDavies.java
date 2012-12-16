package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class MattDavies extends DailyGoComicsCom {
	public MattDavies() {
		super();
		mComicName = "mattdavies";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2001, 3, 23);
	}
}
