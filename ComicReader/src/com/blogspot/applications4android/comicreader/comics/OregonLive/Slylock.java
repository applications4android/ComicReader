

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class Slylock extends DailyOregonlive {
	public Slylock() {
		super();
		mComicFullName = "Slylock Fox";
		mComicName = "Slylock";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}
}
