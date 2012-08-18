

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class Bleeker extends DailyOregonlive {
	public Bleeker() {
		super();
		mComicFullName = "Bleeker the Rechargeable Dog";
		mComicName = "Bleeker";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 0, 3); 
	}

}
