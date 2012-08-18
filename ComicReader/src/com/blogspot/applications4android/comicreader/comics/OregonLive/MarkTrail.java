

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class MarkTrail extends DailyOregonlive {
	public MarkTrail() {
		super();
		mComicFullName = "Mark Trail";
		mComicName = "Mark_Trail";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}

}
