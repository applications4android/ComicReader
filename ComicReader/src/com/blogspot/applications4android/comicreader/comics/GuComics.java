package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class GuComics extends ArchivedComic {
	
	@Override
	public String getComicWebPageUrl() {
		return "http://gucomics.com/comic/";
	}

	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		int idx = 0;
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str,str_temp;
		int i;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("A href=\"/comic/?cdate=");
			if (i != -1) {
				str_temp = str;
				str_temp=str_temp.replaceAll(".*href=\"","");
				str_temp=str_temp.replaceAll("\".*","");
				str_temp="http://gucomics.com/"+str_temp;
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
		return "http://gucomics.com/archives/default.php?yr=all";
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
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("IMG src=\"/comics/");
			if (index1 != -1) {
				final_str = str;
			}
			int index2 = str.indexOf("Title:");
			if (index2 != -1) {
				final_title = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_str = "http://gucomics.com/"+final_str;
		final_title = final_title.replaceAll(".*<B>","");
		final_title = final_title.replaceAll("</B>.*","");
		strip.setTitle("Gu Comics: "+final_title); 
		strip.setText(" -NA- ");
		return final_str;
	}
}
