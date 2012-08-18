   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class ClayBennett extends DailyGoComicsCom {
	public ClayBennett() {
		super();
		mComicName = "claybennett";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2008, 0, 16);
	}
}
