

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class EdgeCity extends DailyOregonlive {
	public EdgeCity() {
		super();
		mComicFullName = "Edge City";
		mComicName = "Edge_City";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}
}
