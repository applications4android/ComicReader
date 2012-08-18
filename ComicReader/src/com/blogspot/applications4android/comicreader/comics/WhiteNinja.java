package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class WhiteNinja extends ArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.whiteninjacomics.com";
	}

	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		int idx = 0;
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str,str_temp;
		int i;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("<a href=/comics/");
			if (i != -1) {
				str_temp = str;
				str_temp=str_temp.replaceAll(".*?href=","");
				str_temp=str_temp.replaceAll("onfocus=\".*","");
				str_temp=str_temp.replaceAll(" $", "");
				str_temp="http://www.whiteninjacomics.com"+str_temp;
				m_com.add(str_temp);
				idx++;
			}
		}
		String[] m_com_urls = new String[idx];
		m_com.toArray(m_com_urls);
		return m_com_urls;
	}

	@Override
	protected String getArchiveUrl() {
		return "http://www.whiteninjacomics.com/archive-comics.shtml";
	}

	@Override
	protected boolean htmlNeeded() {
		return false;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String  url_link = url;		
		url_link = url_link.replaceAll("comics/","images/comics/");
		url_link = url_link.replaceAll(".shtml",".gif");
		String str_title = url_link.replaceAll(".*comics/","");
		str_title = url_link.replaceAll(".*comics/","");
		str_title = str_title.replaceAll(".gif","");
		str_title = "White Ninja: "+str_title;
		strip.setTitle(str_title);
		strip.setText("-NA-");
		return url_link;
	}
}
