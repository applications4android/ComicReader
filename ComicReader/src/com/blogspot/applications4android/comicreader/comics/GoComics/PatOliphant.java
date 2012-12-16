package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class PatOliphant extends DailyGoComicsCom {
	public PatOliphant() {
		super();
		mComicName = "patoliphant";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1980, 2, 30);
	}
}
