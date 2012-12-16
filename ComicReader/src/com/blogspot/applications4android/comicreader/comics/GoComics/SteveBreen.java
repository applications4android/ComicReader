package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class SteveBreen extends DailyGoComicsCom {
	public SteveBreen() {
		super();
		mComicName = "stevebreen";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2008, 7, 1);
	}
}
