package com.blogspot.applications4android.comicreader.comics.DailyKingFeatures;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyKingFeaturesComic;


public class Blondie extends DailyKingFeaturesComic {

	public Blondie() {
		super();
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(1999, 11, 4);  // 1999-Dec-04
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://blondie.com";
	}
}
