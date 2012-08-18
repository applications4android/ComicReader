package com.blogspot.applications4android.comicreader.comics.OregonLive;

import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.DailyOregonlive;


public class FlashGordon extends DailyOregonlive {
	public FlashGordon() {
		super();
		mComicFullName = "Flash Gordon";
		mComicName = "Flash";
		mFirstCal = Calendar.getInstance();
		mFirstCal.set(2007, 10, 4);
	}

	// Flash Gordon comes only on Sundays!
	@Override
	public void addException(Calendar in, int increment) {
		int day = in.get(Calendar.DAY_OF_WEEK);
		if(day == Calendar.SUNDAY) {
			return;
		}
		else if(increment > 0) {
			in.add(Calendar.DATE, 7 - day + Calendar.SUNDAY);
		}
		else {
			in.add(Calendar.DATE, Calendar.SUNDAY - day);
		}
	}

	@Override
	protected Calendar getLatestCalendar() {
		Calendar in = Calendar.getInstance(m_zone);
		addException(in, -1);
		return in;
	}
}

/*
public class FlashGordon extends ArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.oregonlive.com/comics-kingdom/index.ssf?feature_id=Flash";
	}

	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		Calendar cal=Calendar.getInstance();
		int curr_year=cal.get(Calendar.YEAR);
		int curr_mon=cal.get(Calendar.MONTH)+1;
		int curr_day=cal.get(Calendar.DAY_OF_MONTH);
		int curr_date = curr_year*10000 + curr_mon*100+curr_day;
		int idx = 0;
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str,str_temp;
		int i,j;
		int pr_date;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("_large.gif");
			j = str.indexOf("href=\"Flash.");
			if ((i != -1) && (j != -1)) {
				str_temp = str;
				str_temp=str_temp.replaceAll(".*href=\"","");
				str_temp=str_temp.replaceAll("\".*","");
				pr_date = Integer.parseInt(str_temp.replaceAll("Flash.", "").replaceAll("_large.*", ""));
				if (pr_date <= curr_date) {
					str_temp="http://content.comicskingdom.net/Flash/"+str_temp;
					m_com.add(str_temp);
					idx++;
				}
			}
		}
		String []m_com_urls = new String[idx];
		m_com.toArray(m_com_urls);
		return m_com_urls;
	}

	@Override
	protected String getArchiveUrl() {
		return "http://content.comicskingdom.net/Flash/";
	}

	@Override
	protected boolean htmlNeeded() {
		return false;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String val = url;
		String index = val.substring(val.length()-18,val.length()-10);
		strip.setTitle("Flash Gordon" + ": " + index);
		strip.setText("-NA-");
		return url;
	}
}
*/