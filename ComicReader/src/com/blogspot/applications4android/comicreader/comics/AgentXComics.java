package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class AgentXComics extends ArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.agent-x.com.au/";
	}

	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str;
		String str_temp = null;
		int i;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("My previous comics");
			if (i != -1) {
				str_temp = str;
			}
		}
		if(str_temp == null) {
			return null;
		}
		String[] str_split = str_temp.split("<option value=\"");
		for(String temp : str_split) {
			if(!temp.contains("agent-x.com.au")) {
				continue;
			}
			String t = temp.replaceAll("\".*", "");
			m_com.add(t);
		}
		String []m_com_urls = new String[m_com.size()];
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
	protected String getArchiveUrl() {
		return "http://www.agent-x.com.au/";
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip) throws IOException  {
		String str;
		String final_str = null;
		String final_title = url.replaceAll("http.*au/comic/", "");
		final_title = "AgentX: " + final_title.replaceAll("/", "");
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("webcomic-object-full");
			if (index1 != -1) {
				final_str = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		strip.setTitle(final_title); 
		strip.setText("-NA-");
	    return final_str;
	}
}
