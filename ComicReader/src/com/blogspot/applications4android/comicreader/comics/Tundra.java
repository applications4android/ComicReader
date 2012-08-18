package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class Tundra extends ArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.tundracomics.com/";
	}

	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		int idx = 0;
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str,str_temp;
		String [] link_temp;
		int i,j;
		Calendar cal=Calendar.getInstance();
		int curr_year=cal.get(Calendar.YEAR)-2000;
		int curr_month=cal.get(Calendar.MONTH)+1;
		int curr_day=cal.get(Calendar.DAY_OF_MONTH);
		while((str = reader.readLine()) != null) {
			i = str.indexOf("To Parent Directory");
			if (i != -1) {
				String[] links = str.split("HREF");
				for (j=0; j<links.length; j++) {
					if(links[j].indexOf("WEB") != -1) {
						continue;
					}
					if (links[j].indexOf("thisweekstundra") != -1) {
						links[j] = links[j].replaceAll(".*thisweekstundra/", "");
						links[j] = links[j].replaceAll(".jpg.*", "");
						link_temp = links[j].split("-");
						str_temp = link_temp[2]+"-"+link_temp[0]+"-"+link_temp[1];
						int comic_year = Integer.parseInt(link_temp[2]);
						int comic_month = Integer.parseInt(link_temp[0]);
						int comic_day = Integer.parseInt(link_temp[1]);
						if ((curr_year>comic_year) || (curr_year==comic_year && curr_month>comic_month) || (curr_year==comic_year && curr_month==comic_month  && curr_day>=comic_day)) {
							m_com.add(str_temp);
							idx++;
						}
					}
				}
			}
		}
		String []m_com_urls = new String[idx];
		m_com.toArray(m_com_urls);
		Arrays.sort(m_com_urls);
		for (j=0; j<m_com_urls.length; j++) {
			link_temp = m_com_urls[j].split("-");
			m_com_urls[j] = link_temp[1]+"-"+link_temp[2]+"-"+link_temp[0];
			m_com_urls[j] = "http://www.tundracomics.com/thisweekstundra/"+m_com_urls[j]+".jpg";
		}
		return m_com_urls;
	}

	@Override
	protected String getArchiveUrl() {
		return "http://www.tundracomics.com/thisweekstundra/";
	}

	@Override
	protected boolean htmlNeeded() {
		return false;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String url_str = url;
		strip.setTitle("Tundra:"+url_str.substring(url_str.length()-12, url_str.length()-4)); 
		strip.setText("-NA-");
		return url_str;
	}
}
