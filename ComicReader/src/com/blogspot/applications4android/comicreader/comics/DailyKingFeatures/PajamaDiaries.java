package com.blogspot.applications4android.comicreader.comics.DailyKingFeatures;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyKingFeaturesComic;

public class PajamaDiaries extends DailyKingFeaturesComic {

	public PajamaDiaries() {
		super();
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 10, 17);
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://pajamadiaries.com";
	}
}
