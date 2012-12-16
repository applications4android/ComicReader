package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class WorkingDaze extends DailyGoComicsCom {
	public WorkingDaze() {
		super();
		mComicName = "working-daze";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 5, 1);
	}
}
