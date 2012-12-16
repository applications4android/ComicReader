package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class OrdinaryBill extends DailyGoComicsCom {
	public OrdinaryBill() {
		super();
		mComicName = "ordinary-bill";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 1, 26);
	}
}
