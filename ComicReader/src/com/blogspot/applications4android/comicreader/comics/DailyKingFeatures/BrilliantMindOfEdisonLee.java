package com.blogspot.applications4android.comicreader.comics.DailyKingFeatures;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyKingFeaturesComic;

public class BrilliantMindOfEdisonLee extends DailyKingFeaturesComic {

	public BrilliantMindOfEdisonLee() {
		super();
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2006, 10, 12);
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://edisonleecomic.com";
	}
}
