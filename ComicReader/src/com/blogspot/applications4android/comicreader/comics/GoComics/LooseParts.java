package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class LooseParts extends DailyGoComicsCom {
	public LooseParts() {
		super();
		mComicName = "looseparts";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2001, 3, 8);
	}
}
