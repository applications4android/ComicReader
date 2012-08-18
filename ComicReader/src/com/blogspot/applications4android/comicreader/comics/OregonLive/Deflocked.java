

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class Deflocked extends DailyOregonlive {
	public Deflocked() {
		super();
		mComicFullName = "DeFlocked";
		mComicName = "Deflocked";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2008, 4, 5); 
	}
}
