   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class DeepCover extends DailyGoComicsCom {
	public DeepCover() {
		super();
		mComicName = "deepcover";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2002, 2, 27);
	}
}
