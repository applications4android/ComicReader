

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class RexMorgan extends DailyOregonlive {
	public RexMorgan() {
		super();
		mComicFullName = "Rex Morgan M.D.";
		mComicName = "Rex_Morgan";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}
}
