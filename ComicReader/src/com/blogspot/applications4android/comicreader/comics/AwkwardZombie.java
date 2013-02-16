package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;

public class AwkwardZombie  extends ArchivedComic  {
	@Override
	public String getComicWebPageUrl() {
		return "http://awkwardzombie.com";
	}

	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		int idx = 0;
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str,str_temp;
		Pattern p = Pattern.compile("index\\.php\\?page=0&comic=\\d{6}");
		while((str = reader.readLine()) != null) {
		
			Matcher m = p.matcher(str);
			while (m.find()) {
				str_temp = "http://awkwardzombie.com/" + m.group();
				m_com.add(str_temp);
				idx++;
			}
		}
		Collections.reverse(m_com);
		
		String[] m_com_urls = new String[idx];
		m_com.toArray(m_com_urls);
		return m_com_urls;
	}

	@Override
	protected String getArchiveUrl() {
		return "http://awkwardzombie.com/index.php?page=1";
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		boolean flag = false;
		String str = null;
		String final_url = null;
		String final_title = null;
		String final_itext = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("http://i49.photobucket.com/albums/");
			if (index1 != -1) {
				final_url = str;
				
			}
			int index2 = str.indexOf("<div class=\"title centered\">");
			if (index2 != -1) {
				final_title = reader.readLine();
			}
			int index3 = str.indexOf("<span id=\"date\">");
			if (index3 != -1) {
				 flag = true;
			}
			if (flag && str.length()>20 ){
				final_itext = android.text.Html.fromHtml(str).toString();
				flag = false;
			}
			

		}
		
		
		final_url = final_url.replaceAll(".*src=\"","");
		final_url = final_url.replaceAll("\".*","");
     	final_title = final_title.replaceAll("<.*","");
		strip.setTitle("Awkward Zombie " + final_title);
		strip.setText(final_itext);
		return final_url; 

		
	}

}
