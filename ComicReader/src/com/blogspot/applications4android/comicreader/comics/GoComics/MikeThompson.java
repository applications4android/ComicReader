package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class MikeThompson extends DailyGoComicsCom {
	public MikeThompson() {
		super();
		mComicName = "mikethompson";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2008, 3, 8);
	}
}
