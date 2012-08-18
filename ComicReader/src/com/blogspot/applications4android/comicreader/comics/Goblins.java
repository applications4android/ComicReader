package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class Goblins extends ArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.goblinscomic.com";
	}

	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		int idx = 0;
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str,str_temp;
		int i;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("<li><a href=\"");
			if (i != -1) {
				str_temp = str;
				str_temp=str_temp.replaceAll(".*href=\"","");
				str_temp=str_temp.replaceAll("\".*","");
				if(str_temp.indexOf("goblinscomic.com") == -1) {
					str_temp = "http://www.goblinscomic.com/" + str_temp;
				}
				m_com.add(str_temp);
				idx++;
			}
		}
		String []m_com_urls = new String[idx];
		m_com.toArray(m_com_urls);
		/*
	    int left, right;
	    String temp;
		for (left=0, right=m_com_urls.length-1; left<right; left++, right--) {
		    temp = m_com_urls[left]; 
		    m_com_urls[left]  = m_com_urls[right]; 
		    m_com_urls[right] = temp;
		}
		*/
		return m_com_urls;
	}

	@Override
	protected String getArchiveUrl() {
		return "http://www.goblinscomic.com/archive/";
	}

	@Override
	protected boolean htmlNeeded() {
		return false;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip) throws IOException  {
		String str = url.replaceAll("http://www.goblinscomic.com/", "");
		str = str.replaceAll("/", "");
		str = str.replaceAll("-.*", "");
		char[] chars = str.toCharArray();
		char[] correct_chars = new char[chars.length];
		correct_chars[0] = chars[4];
		correct_chars[1] = chars[5];
		correct_chars[2] = chars[6];
		correct_chars[3] = chars[7];
		correct_chars[4] = chars[0];
		correct_chars[5] = chars[1];
		correct_chars[6] = chars[2];
		correct_chars[7] = chars[3];
		str = new String(correct_chars);
		String final_str = "http://www.goblinscomic.com/comics/" + str + ".jpg";
		String final_title = "Goblins: " + str;
		strip.setTitle(final_title); 
		strip.setText("-NA-");
	    return final_str;
	}
}
