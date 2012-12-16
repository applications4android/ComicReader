package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class LuckyCow extends DailyGoComicsCom {
	public LuckyCow() {
		super();
		mComicName = "luckycow";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2003, 3, 21);
	}
}
