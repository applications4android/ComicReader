package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class RicigsToonTrivia extends DailyGoComicsCom {
	public RicigsToonTrivia() {
		super();
		mComicName = "ricigs-toon-trivia";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 11, 4);
	}
}
