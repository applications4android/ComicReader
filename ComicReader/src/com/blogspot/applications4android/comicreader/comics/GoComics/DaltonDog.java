   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class DaltonDog extends DailyGoComicsCom {
	public DaltonDog() {
		super();
		mComicName = "dalton-dog";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 11, 13);
	}
}
