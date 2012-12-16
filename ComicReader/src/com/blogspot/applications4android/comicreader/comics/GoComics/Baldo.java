package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Baldo extends DailyGoComicsCom {
	public Baldo() {
		super();
		mComicName = "baldo";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1998, 10, 22);
	}
}
