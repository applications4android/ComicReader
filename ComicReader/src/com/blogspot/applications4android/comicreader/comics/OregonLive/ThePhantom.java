

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class ThePhantom extends DailyOregonlive {
	public ThePhantom() {
		super();
		mComicFullName = "Phantom";
		mComicName = "Phantom";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}
}