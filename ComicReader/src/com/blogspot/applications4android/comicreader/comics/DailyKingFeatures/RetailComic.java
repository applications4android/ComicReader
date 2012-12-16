package com.blogspot.applications4android.comicreader.comics.DailyKingFeatures;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyKingFeaturesComic;

public class RetailComic extends DailyKingFeaturesComic {

	public RetailComic() {
		super();
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2006, 0, 1);
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://retailcomic.com";
	}
}
