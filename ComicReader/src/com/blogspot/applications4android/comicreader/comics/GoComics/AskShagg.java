package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class AskShagg extends DailyGoComicsCom {
	public AskShagg() {
		super();
		mComicName = "askshagg";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2002, 7, 12);
	}
}
