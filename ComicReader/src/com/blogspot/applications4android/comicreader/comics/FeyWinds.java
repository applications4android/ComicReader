package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class FeyWinds extends ArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://kitsune.rydia.net/comic.html";
	}

	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		int idx = 0;
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str,str_temp;
		int i;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("alt=\"[IMG]");
			if (i != -1) {
				str_temp = str;
				str_temp=str_temp.replaceAll(".*href=\"","");
				str_temp=str_temp.replaceAll("\".*","");
				str_temp="http://kitsune.rydia.net/comic/pages/"+str_temp;
				m_com.add(str_temp);
				idx++;
			}
		}
		String []m_com_urls = new String[idx];
		m_com.toArray(m_com_urls);
		return m_com_urls;
	}

	@Override
	protected String getArchiveUrl() {
		return "http://kitsune.rydia.net/comic/pages/";
	}

	@Override
	protected boolean htmlNeeded() {
		return false;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String val = url;
		String index = val.substring(val.length()-8,val.length()-4);
		strip.setTitle("Fey Winds" + ": " + index);
		strip.setText("-NA-");
		return url;
	}
}
