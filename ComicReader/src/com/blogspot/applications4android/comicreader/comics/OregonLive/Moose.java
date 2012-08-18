

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class Moose extends DailyOregonlive {
	public Moose() {
		super();
		mComicFullName = "Moose and Molly";
		mComicName = "Moose";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}
}
