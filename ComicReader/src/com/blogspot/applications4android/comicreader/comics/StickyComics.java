package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class StickyComics extends ArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.stickycomics.com/";
	}

	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str,str_temp;
		int i;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("archiveImg");
			if (i != -1) {
				str_temp = str;
				str_temp=str_temp.replaceAll(".*href=\"","");
				str_temp=str_temp.replaceAll("\".*","");
				m_com.add(str_temp);
			}
		}
		Collections.reverse(m_com);
		String[] m_com_urls = new String[m_com.size()];
		m_com.toArray(m_com_urls);
		return m_com_urls;
	}

	@Override
	protected String getArchiveUrl() {
		return "http://www.stickycomics.com/all/comics/";
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String str;
		String final_str = null;
		String final_title = null;
		String final_itext = null;
		
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("size-full wp-image");
			if (index1 != -1) {
				final_itext = final_str = str;
			}
			int index2 = str.indexOf("sticky comics</title>");
			if (index2 != -1) {
				final_title = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"http://www.stickycomics","http://www.stickycomics");
		final_str = final_str.replaceAll("\".*","");
		final_itext = final_itext.replaceAll(".*title=\"","");
		final_itext = final_itext.replaceAll("\" src.*","");
		final_title = final_title.replaceAll(".*<title>  ","");
		final_title = final_title.replaceAll("</title>.*","");
		strip.setTitle(final_title); 
		strip.setText(final_itext);
		return final_str;
	}
}
