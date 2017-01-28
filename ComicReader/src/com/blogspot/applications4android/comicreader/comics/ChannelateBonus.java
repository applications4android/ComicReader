package com.blogspot.applications4android.comicreader.comics;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class ChannelateBonus extends ArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.channelate.com/";
	}

	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		int idx = 0;
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str,str_temp;
		int i;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("class=\"archive-title\"");
			if (i != -1) {
				str_temp = str;
				str_temp=str_temp.replaceAll(".*href=\"","");
				str_temp=str_temp.replaceAll("\".*","");
                str_temp=str_temp.replaceAll(".*com/","");
                str_temp=str_temp.replaceFirst("/","");
                str_temp=str_temp.replaceFirst("/","");
                str_temp=str_temp.replaceAll("/.*","/");
                str_temp="http://www.channelate.com/extra-panel/"+str_temp;
				m_com.add(str_temp);
				idx++;
			}
		}
		String []m_com_urls = new String[idx];
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
		return "http://www.channelate.com/note-to-self-archive/";
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip) throws IOException  {
		String str;
		String final_str = null;
		String final_title = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("extrapanelimage");
			if (index1 != -1) {
				final_str = str;
				final_title = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","http:");
        final_str = final_str.replaceAll("\".*","");
        final_title = final_title.replaceAll(".*extrapanels/","");
        final_title = final_title.replaceAll("\".*","");
        final_title = final_title.replaceAll("-EX.png","");
		strip.setTitle("Channelate Bonus: "+final_title);
		strip.setText("-NA-");
	    return final_str;
	}
}
