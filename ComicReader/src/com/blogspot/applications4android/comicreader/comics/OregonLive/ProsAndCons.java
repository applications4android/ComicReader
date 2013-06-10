package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;

public class ProsAndCons extends DailyOregonlive {
	public ProsAndCons() {
		super();
		mComicFullName = "Pros & Cons";
		mComicName = "Pros_and_Cons";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2013, 2, 4); 
	}
}
