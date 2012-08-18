   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class CitizenDog extends DailyGoComicsCom {
	public CitizenDog() {
		super();
		mComicName = "citizendog";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1995, 4, 15);
	}
}
