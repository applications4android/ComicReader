   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class GetFuzzy extends DailyGoComicsCom {
	public GetFuzzy() {
		super();
		mComicName = "getfuzzy";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1999, 8, 6);
	}
}
