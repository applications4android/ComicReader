package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class KenCatalino extends DailyGoComicsCom {
	public KenCatalino() {
		super();
		mComicName = "kencatalino";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2005, 0, 4);
	}
}
