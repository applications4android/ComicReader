   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TheElderberries extends DailyGoComicsCom {
	public TheElderberries() {
		super();
		mComicName = "theelderberries";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2004, 11, 6);
	}
}
