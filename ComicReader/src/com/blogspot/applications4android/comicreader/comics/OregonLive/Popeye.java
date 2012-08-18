

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class Popeye extends DailyOregonlive {
	public Popeye() {
		super();
		mComicFullName = "Popeye";
		mComicName = "Popeye";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}
}