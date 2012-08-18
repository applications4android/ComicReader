

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class PardonMyPlanet extends DailyOregonlive {
	public PardonMyPlanet() {
		super();
		mComicFullName = "Pardon My Planet";
		mComicName = "Pardon_My_Planet";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}
}