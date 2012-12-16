package com.blogspot.applications4android.comicreader.comics.DailyKingFeatures;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyKingFeaturesComic;

public class RhymesWithOrange extends DailyKingFeaturesComic {

	public RhymesWithOrange() {
		super();
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2000, 0, 1);
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://rhymeswithorange.com";
	}
}
