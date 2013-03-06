package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;

public class GrrlPower extends ArchivedComic{

	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		ArrayList<String> m_com = new ArrayList<String>();
		String str, str_temp;
		String search = "archive-date";
		int i;
		while ((str = reader.readLine()) != null) {
			i = str.indexOf(search);
			if (i != -1) {
					str_temp = str;
					str_temp = str_temp.replaceAll(".*?href=\"", "");
					str_temp = str_temp.replaceAll("\".*$", "");
					m_com.add(str_temp);
			}
		}
		//Reverse the list because the newest one was received first.
		Collections.reverse(m_com);
		
		String[] m_com_urls = new String[m_com.size()];
		m_com.toArray(m_com_urls);
		return m_com_urls;
	}

	@Override
	protected String getArchiveUrl() {
		return "http://www.grrlpowercomic.com/archives";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.grrlpowercomic.com/";
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
		while ((str = reader.readLine()) != null) {
			// This gets the location of the image
			int index1 = str.indexOf("<div id=\"comic\">");
			if (index1 != -1) {
				final_str = str;
			}
			// This gets the title of the commic
			int index2 = str.indexOf("<title>Grrl Power");
			if (index2 != -1) {
				final_title = str;
			}
		}
		// Pattern matching to remove unwanted characters.
		final_str = final_str.replaceAll(".*img src=\"", "");
		final_str = final_str.replaceAll("\".*", "");
		final_title = final_title.replaceAll(".*- ", "");
		final_title = final_title.replaceAll("</title>.*", "");
		strip.setTitle(final_title);
		return final_str;

	}

}
