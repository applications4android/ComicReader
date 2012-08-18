

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class Ollie extends DailyOregonlive {
	public Ollie() {
		super();
		mComicFullName = "Ollie and Quentin";
		mComicName = "Ollie";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2008, 0, 7); 
	}
}
