   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class ViewsoftheWorld extends DailyGoComicsCom {
	public ViewsoftheWorld() {
		super();
		mComicName = "viewsoftheworld";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 2, 15);
	}
}
