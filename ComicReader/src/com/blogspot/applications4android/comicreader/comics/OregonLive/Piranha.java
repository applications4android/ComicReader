

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class Piranha extends DailyOregonlive {
	public Piranha() {
		super();
		mComicFullName = "Piranha Club";
		mComicName = "Piranha";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}
}
