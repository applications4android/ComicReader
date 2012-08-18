

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class Tiger extends DailyOregonlive {
	public Tiger() {
		super();
		mComicFullName = "Tiger";
		mComicName = "Tiger";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}
}
