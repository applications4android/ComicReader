   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Doonesbury extends DailyGoComicsCom {
	public Doonesbury() {
		super();
		mComicName = "doonesbury";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1970, 9, 26);
	}
}
