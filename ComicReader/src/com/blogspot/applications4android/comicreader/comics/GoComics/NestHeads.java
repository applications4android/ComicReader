package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class NestHeads extends DailyGoComicsCom {
	public NestHeads() {
		super();
		mComicName = "nestheads";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2003, 4, 30);
	}
}
