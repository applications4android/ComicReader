   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class ViewsAmerica extends DailyGoComicsCom {
	public ViewsAmerica() {
		super();
		mComicName = "viewsamerica";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 1, 4);
	}
}
