

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class ShermansLagoon extends DailyOregonlive {
	public ShermansLagoon() {
		super();
		mComicFullName = "Sherman's Lagoon";
		mComicName = "Shermans_Lagoon";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}
}