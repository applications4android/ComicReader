   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class WaltHandelsman extends DailyGoComicsCom {
	public WaltHandelsman() {
		super();
		mComicName = "walthandelsman";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2001, 3, 23);
	}
}
