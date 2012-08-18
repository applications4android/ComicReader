package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class AmazingSuperpowers extends ArchivedComic {

	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		int idx = 0;
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str,str_temp;
		int i;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("title=\"Permanent Link:");
			if (i != -1) {
				str_temp = str;
				str_temp=str_temp.replaceAll(".*href=\"","");
				str_temp=str_temp.replaceAll("\".*","");
				m_com.add(str_temp);
				idx++;
			}
		}
		String[] m_com_urls = new String[idx];
		m_com.toArray(m_com_urls);
	    int left, right;
	    String temp;
		for (left=0, right=m_com_urls.length-1; left<right; left++, right--) {
		    temp = m_com_urls[left]; 
		    m_com_urls[left]  = m_com_urls[right]; 
		    m_com_urls[right] = temp;
		}
		return m_com_urls;
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.amazingsuperpowers.com/";
	}

	@Override
	protected String getArchiveUrl() {
		return "http://www.amazingsuperpowers.com/category/comics/";
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
		String final_itext = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("class=\"comicpane\"");
			if (index1 != -1) {
				final_str = str;
				final_itext = str;
				final_title = str;
				}
		}
		final_str = final_str.replaceAll(".*img src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_title = final_title.replaceAll(".*comics/","");
		final_title = final_title.replaceAll(".png.*","");
		final_title = final_title.replaceAll(".jpg.*","");
		final_itext = final_itext.replaceAll(".*title=\"","");
		final_itext = final_itext.replaceAll("\".*","");
		strip.setTitle("Amazing Superpowers: "+final_title); 
		strip.setText(final_itext);
		return final_str;
	}

}
