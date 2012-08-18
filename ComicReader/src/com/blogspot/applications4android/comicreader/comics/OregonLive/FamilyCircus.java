
package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class FamilyCircus extends DailyOregonlive {
	public FamilyCircus() {
		super();
		mComicFullName = "Family Circus";
		mComicName = "Family_Circus";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}

}