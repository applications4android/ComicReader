package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class BergernWyse extends DailyGoComicsCom {
	public BergernWyse() {
		super();
		mComicName = "berger-and-wyse";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 6, 20);
	}
}
