

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class Bizarro extends DailyOregonlive {
	public Bizarro() {
		super();
		mComicFullName = "Bizarro";
		mComicName = "Bizarro";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}

	public void addException(Calendar in, int increment) {
		// exception
		int yr = in.get(Calendar.YEAR);
		int mon = in.get(Calendar.MONTH);
		int dat = in.get(Calendar.DAY_OF_MONTH);
		if(yr == 2007 && mon == 10 && dat == 21) {
			in.add(Calendar.DAY_OF_MONTH, increment);			
		}
	}
}