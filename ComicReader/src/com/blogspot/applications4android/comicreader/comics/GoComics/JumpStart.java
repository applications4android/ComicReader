package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class JumpStart extends DailyGoComicsCom {
	public JumpStart() {
		super();
		mComicName = "jumpstart";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1996, 0, 1);
	}
}
