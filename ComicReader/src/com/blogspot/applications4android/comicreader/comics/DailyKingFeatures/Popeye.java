package com.blogspot.applications4android.comicreader.comics.DailyKingFeatures;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyKingFeaturesComic;

public class Popeye extends DailyKingFeaturesComic {

	public Popeye() {
		super();
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2011, 4, 12);
	}

	@Override
	protected Calendar getLatestCalendar() {
		if(mLatestCal != null) {
			return mLatestCal;
		}
		mLatestCal = Calendar.getInstance(m_zone);
		mLatestCal.set(2012, 10, 12);
		return mLatestCal;
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://popeye.com";
	}
}
