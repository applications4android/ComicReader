package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class MyCage extends DailyGoComicsCom {
	public MyCage() {
		super();
		mComicName = "mycage";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 1, 28);
	}
}
