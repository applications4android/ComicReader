package com.blogspot.applications4android.comicreader.comics.DailyKingFeatures;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyKingFeaturesComic;

public class ArcticCircle extends DailyKingFeaturesComic {

	public ArcticCircle() {
		super();
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2012, 8, 8);
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://arcticcirclecartoons.com";
	}
}
