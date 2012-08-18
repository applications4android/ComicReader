

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class BarneyGoogle extends DailyOregonlive {
	public BarneyGoogle() {
		super();
		mComicFullName = "Barney Google and Snuffy Smith";
		mComicName = "Barney_Google";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 9, 30); 
	}
}
