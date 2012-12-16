package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class TheBigPicture extends DailyGoComicsCom {
	public TheBigPicture() {
		super();
		mComicName = "thebigpicture";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2010, 10, 29);
	}
}
