package com.blogspot.applications4android.comicreader.comics.DailyKingFeatures;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyKingFeaturesComic;

public class BeetleBailey extends DailyKingFeaturesComic {

	public BeetleBailey() {
		super();
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2005, 3, 30);
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://beetlebailey.com";
	}
}
