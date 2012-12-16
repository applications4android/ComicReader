package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class IncidentalComics extends DailyGoComicsCom {
	public IncidentalComics() {
		super();
		mComicName = "incidentalcomics";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 2, 21);
	}
}
