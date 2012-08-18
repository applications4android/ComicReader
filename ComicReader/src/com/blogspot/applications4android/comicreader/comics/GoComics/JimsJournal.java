   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class JimsJournal extends DailyGoComicsCom {
	public JimsJournal() {
		super();
		mComicName = "jimsjournal";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 3, 22);
	}
}
