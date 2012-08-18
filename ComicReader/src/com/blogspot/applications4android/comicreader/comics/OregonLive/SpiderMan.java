

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class SpiderMan extends DailyOregonlive {
	public SpiderMan() {
		super();
		mComicFullName = "Amazing Spider-Man";
		mComicName = "Spiderman";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}
}