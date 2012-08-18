package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class Buttersafe extends ArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.buttersafe.com";
	}

	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		int idx = 0;
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str;
		while((str = reader.readLine()) != null) {
			if(str.indexOf("class=\"archive-title\"") != -1) {
				str = str.replaceAll(".*?href=\"","");
				str = str.replaceAll("\".*","");
				m_com.add(str);
				idx++;
			}
		}
		String []m_com_urls = new String[idx];
		for(int i=idx-1;i>=0;i--) {
			m_com_urls[idx-i-1] = m_com.get(i);
		}
		return m_com_urls;
	}

	@Override
	protected String getArchiveUrl() {
		return "http://www.buttersafe.com/archive/";
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
		String str;
		String final_str = null;
		String final_title = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("http://buttersafe.com/comics/");
			if (index1 != -1) {
				final_str = str;
				final_title = str;
			}
		}
		final_str = final_str.replaceAll(".*img src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_title = final_title.replaceAll(".*http://buttersafe.com/comics/", "");
		final_title = final_title.replaceAll("\".*", "");
		final_title = "Buttersafe: " + final_title.substring(11, final_title.length());
		final_title = final_title.replaceAll(".jpg", "");
		strip.setTitle(final_title); 
		strip.setText("-NA-");
		return final_str;
	}
}
