package com.blogspot.applications4android.comicreader.comics.DailyKingFeatures;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyKingFeaturesComic;

public class Gil extends DailyKingFeaturesComic {

	public Gil() {
		super();
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 0, 2);
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://gilcomics.com";
	}
}
