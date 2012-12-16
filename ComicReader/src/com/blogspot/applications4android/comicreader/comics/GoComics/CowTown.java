package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class CowTown extends DailyGoComicsCom {
	public CowTown() {
		super();
		mComicName = "cowtown";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2010, 11, 13);
	}
}
