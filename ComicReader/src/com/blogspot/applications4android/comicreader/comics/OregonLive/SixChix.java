

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class SixChix extends DailyOregonlive {
	public SixChix() {
		super();
		mComicFullName = "Six Chix";
		mComicName = "6Chix";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}

	public void addException(Calendar in, int increment) {
		// exception
		int yr = in.get(Calendar.YEAR);
		int mon = in.get(Calendar.MONTH);
		int dat = in.get(Calendar.DAY_OF_MONTH);
		if(yr == 2007 && mon == 11 && (17 <= dat && dat <= 22)) {
			if(increment == 1) {
				in.set(2007, 11, 23);				
			} else if(increment == -1) {
				in.set(2007, 11, 17);				
			}			
		}
		yr = in.get(Calendar.YEAR);
		mon = in.get(Calendar.MONTH);
		dat = in.get(Calendar.DAY_OF_MONTH);
	}
}
