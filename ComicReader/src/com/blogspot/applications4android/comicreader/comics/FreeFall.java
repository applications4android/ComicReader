package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class FreeFall extends ArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://freefall.purrsia.com/";
	}

	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		int idx = 0;
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str,str_temp;
		int i,j;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("<A HREF=\"/ff");
			j = str.indexOf("</A>&nbsp;");
			if (i != -1 && j != -1) {
				str_temp = str;
				str_temp=str_temp.replaceAll(".*HREF=\"","");
				str_temp=str_temp.replaceAll("\".*","");
				str_temp="http://freefall.purrsia.com/"+str_temp;
				m_com.add(str_temp);
				idx++;
			}
		}
		m_com.add("http://freefall.purrsia.com/default.htm");
		idx++;
	    String []m_com_urls = new String[idx];
		m_com.toArray(m_com_urls);
	    return m_com_urls;
	}

	@Override
	protected String getArchiveUrl() {
		return "http://freefall.purrsia.com/fcdex.htm";
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
		String exten = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.toLowerCase().indexOf("img src=");
			if ((index1 != -1) && (final_str == null) ) {
				final_str = str;
			}
		}
		final_str = final_str.toLowerCase().replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		int dot = final_str.lastIndexOf(".");
		exten = final_str.substring(dot+1);
				String image = url.replace("htm", exten);
		if (image.indexOf("default") != -1) {
			image = "http://freefall.purrsia.com/"+final_str;
		}
		strip.setTitle("Free Fall : " + image.replaceAll(".*com//", "").replaceAll("."+exten, ""));
		strip.setText("-NA-");
		return image;
	}
}
