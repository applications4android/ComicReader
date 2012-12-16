package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class GetaLife extends DailyGoComicsCom {
	public GetaLife() {
		super();
		mComicName = "getalife";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2010, 3, 14);
	}
}
