   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;


/**
 * Class for Opus comic series.
 */
public class Opus extends DailyGoComicsCom {
	public Opus() {
		super();
		mComicName = "opus";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2005, 2, 27); //2005/03/27
	}

}
