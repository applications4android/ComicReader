

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class FastTrack extends DailyOregonlive {
	public FastTrack() {
		super();
		mComicFullName = "On the Fastrack";
		mComicName = "Fast_Track";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}

	public void addException(Calendar in, int increment) {
		// exception
		int yr = in.get(Calendar.YEAR);
		int mon = in.get(Calendar.MONTH);
		int dat = in.get(Calendar.DAY_OF_MONTH);
		if(yr == 2007 && mon == 11 && (10 <= dat && dat <= 15)) {
			if(increment == 1) {
				in.set(2007, 11, 16);				
			} else if(increment == -1) {
				in.set(2007, 11, 9);				
			}			
		}
		yr = in.get(Calendar.YEAR);
		mon = in.get(Calendar.MONTH);
		dat = in.get(Calendar.DAY_OF_MONTH);	
	}
}
