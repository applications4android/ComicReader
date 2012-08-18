

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class Pajama extends DailyOregonlive {
	public Pajama() {
		super();
		mComicFullName = "Pajama Diaries";
		mComicName = "Pajama";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2008, 0, 6); 
	}
}
