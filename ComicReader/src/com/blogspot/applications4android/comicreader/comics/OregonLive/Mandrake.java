

package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class Mandrake extends DailyOregonlive {
	public Mandrake() {
		super();
		mComicFullName = "Mandrake the Magician";
		mComicName = "Mandrake";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 1); 
	}

	public void addException(Calendar in, int increment) {
		// exception
		int dow = in.get(Calendar.DAY_OF_WEEK);
		if(dow == 1) {
			in.add(Calendar.DAY_OF_MONTH, increment);			
		}
	}

	@Override
	protected Calendar getLatestCalendar() {
		Calendar in = Calendar.getInstance(m_zone);
		addException(in, -1);
		return in;
	}
}
