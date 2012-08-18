   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class BiffnRiley extends DailyGoComicsCom {
	public BiffnRiley() {
		super();
		mComicName = "biff-and-riley";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2010, 0, 12);
	}
}
