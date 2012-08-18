

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class FunkyWinkerbean extends DailyOregonlive {
	public FunkyWinkerbean() {
		super();
		mComicFullName = "Funky Winkerbean";
		mComicName = "Funky_Winkerbean";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}

}
