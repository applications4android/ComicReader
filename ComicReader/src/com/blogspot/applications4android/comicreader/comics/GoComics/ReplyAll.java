package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;
import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

public class ReplyAll extends DailyGoComicsCom {
	public ReplyAll() {
		super();
		mComicName = "replyall";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 1, 28);
	}
}
