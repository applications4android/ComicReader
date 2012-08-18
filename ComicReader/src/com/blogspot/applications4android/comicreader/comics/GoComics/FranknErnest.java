   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class FranknErnest extends DailyGoComicsCom {
	public FranknErnest() {
		super();
		mComicName = "frankandernest";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2000, 6, 2);
	}
}
