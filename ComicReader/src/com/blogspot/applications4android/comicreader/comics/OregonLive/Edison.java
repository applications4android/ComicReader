

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class Edison extends DailyOregonlive {
	public Edison() {
		super();
		mComicFullName = "Brilliant Mind of Edison Lee";
		mComicName = "Edison";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}

}
