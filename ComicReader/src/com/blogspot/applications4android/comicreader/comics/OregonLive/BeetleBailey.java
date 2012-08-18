package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class BeetleBailey extends DailyOregonlive {
	public BeetleBailey() {
		super();
		mComicFullName = "Beetle Bailey";
		mComicName = "Beetle_Bailey";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); // 2007 11 01
	}
}
