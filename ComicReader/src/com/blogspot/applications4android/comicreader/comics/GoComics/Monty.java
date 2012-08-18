   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class Monty extends DailyGoComicsCom {
	public Monty() {
		super();
		mComicName = "monty";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 4, 16);
	}
}
