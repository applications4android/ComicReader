   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class ViewsLatinAmerica extends DailyGoComicsCom {
	public ViewsLatinAmerica() {
		super();
		mComicName = "viewslatinamerica";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 0, 3);
	}
}
