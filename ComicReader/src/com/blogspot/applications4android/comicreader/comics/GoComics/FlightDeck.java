   
package com.blogspot.applications4android.comicreader.comics.GoComics;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyGoComicsCom;

/**
 * Class for Flight Deck comic series.
 */
public class FlightDeck extends DailyGoComicsCom {
	public FlightDeck() {
		super();
		mComicName = "flightdeck";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2002,0,1);
	}
}
