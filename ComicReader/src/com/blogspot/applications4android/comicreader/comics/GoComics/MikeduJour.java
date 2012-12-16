package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class MikeduJour extends DailyGoComicsCom {
	public MikeduJour() {
		super();
		mComicName = "mike-du-jour";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 8, 17);
	}
}
