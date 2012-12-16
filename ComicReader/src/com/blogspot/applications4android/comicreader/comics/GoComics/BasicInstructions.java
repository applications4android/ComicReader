package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class BasicInstructions extends DailyGoComicsCom {
	public BasicInstructions() {
		super();
		mComicName = "basicinstructions";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2003, 1, 25);
	}
}
