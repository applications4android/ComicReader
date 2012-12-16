package com.blogspot.applications4android.comicreader.comics.DailyKingFeatures;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyKingFeaturesComic;


public class Zits extends DailyKingFeaturesComic {

	public Zits() {
		super();
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2005, 0, 1);
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://zitscomics.com";
	}
}
