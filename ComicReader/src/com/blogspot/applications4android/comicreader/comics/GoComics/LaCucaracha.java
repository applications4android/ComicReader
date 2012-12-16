package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class LaCucaracha extends DailyGoComicsCom {
	public LaCucaracha() {
		super();
		mComicName = "lacucaracha";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2002, 10, 25);
	}
}
