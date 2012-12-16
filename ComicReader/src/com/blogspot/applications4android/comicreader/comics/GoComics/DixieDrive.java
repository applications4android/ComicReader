package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class DixieDrive extends DailyGoComicsCom {
	public DixieDrive() {
		super();
		mComicName = "dixie-drive";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 8, 18);
	}
}
