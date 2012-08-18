   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class JoelPett extends DailyGoComicsCom {
	public JoelPett() {
		super();
		mComicName = "joelpett";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1999, 8, 20);
	}
}
