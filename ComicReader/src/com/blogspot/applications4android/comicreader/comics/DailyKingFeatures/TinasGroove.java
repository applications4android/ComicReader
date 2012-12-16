package com.blogspot.applications4android.comicreader.comics.DailyKingFeatures;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyKingFeaturesComic;

public class TinasGroove extends DailyKingFeaturesComic {

	public TinasGroove() {
		super();
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2005, 11, 31);
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://tinasgroove.com";
	}
}
