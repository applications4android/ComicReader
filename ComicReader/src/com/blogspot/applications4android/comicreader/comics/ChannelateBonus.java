package com.blogspot.applications4android.comicreader.comics;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Pattern;

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
		String final_date="";
		int i;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("class=\"archive-title\"");
			String s2 = "Permanent Link: short";
			if (i != -1) {
				System.out.println("str" + str);
				str_temp = str;
				String[] str_archive_list = str_temp.split("</tr><tr>");
				for(String str_archive:str_archive_list){
					String str_date = str_archive;
					str_archive = str_archive.replaceAll(".*href=\"", "");
					str_archive = str_archive.replaceAll("\".*", "");
					if (str_archive.contains("comic/short")) {
						continue;
					} else {
						str_date = str_date.replaceAll(".*archive-date\">", "");
						str_date = str_date.replaceAll("</td>.*","");
						str_date = str_date + " 2018";
						SimpleDateFormat oneformatter = new SimpleDateFormat("MMM dd yyyy");
						SimpleDateFormat twoformatter = new SimpleDateFormat("yyyyMMdd");
						try {
							Date date = oneformatter.parse(str_date);
							final_date = twoformatter.format(date);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						final_date = "http://www.channelate.com/extra-panel/" + final_date;
						m_com.add(final_date);
						idx++;
					}
				}
			}
		}
		String []m_com_urls = new String[idx];
		Collections.reverse(m_com);
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
		return "http://www.channelate.com/comic-archives/";
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
		final_str = final_str.replaceAll("http:http","http");
        final_str = final_str.replaceAll("\".*","");
        final_title = final_title.replaceAll(".*extrapanels/","");
        final_title = final_title.replaceAll("\".*","");
        final_title = final_title.replaceAll("-EX.png","");
		strip.setTitle("Channelate Bonus: "+final_title);
		strip.setText("-NA-");
	    return final_str;
	}
}
