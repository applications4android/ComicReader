

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class Arctic extends DailyOregonlive {
	public Arctic() {
		super();
		mComicFullName = "Arctic Circle";
		mComicName = "Arctic";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}
}
