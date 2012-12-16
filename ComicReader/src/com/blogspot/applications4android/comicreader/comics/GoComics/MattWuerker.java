package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class MattWuerker extends DailyGoComicsCom {
	public MattWuerker() {
		super();
		mComicName = "mattwuerker";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 2, 8);
	}
}
