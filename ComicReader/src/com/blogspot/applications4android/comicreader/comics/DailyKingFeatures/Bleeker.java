package com.blogspot.applications4android.comicreader.comics.DailyKingFeatures;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyKingFeaturesComic;

public class Bleeker extends DailyKingFeaturesComic {

	public Bleeker() {
		super();
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 0, 3);
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://bleekercomics.com";
	}
}
