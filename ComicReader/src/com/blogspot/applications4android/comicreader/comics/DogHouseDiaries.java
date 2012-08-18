package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;

/**
 * Class for DogHouseDiaries
 */
public class DogHouseDiaries extends ArchivedComic {
	
	/**
	 * Get the main page url for the current comic series
	 * @return desired url
	 */
	@Override
	public String getComicWebPageUrl() {
		return "http://www.thedoghousediaries.com/";
	}

	/**
	 * Main function responsible for parsing the archive url and generating the
	 * list of all comic urls.
	 * @param reader buffered reader from which to parse the comic urls.
	 * @return array of comic url strings
	 * @throws Exception
	 */
	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		int idx = 0;
		
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str,str_temp;
		int i;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("comic-archive-item comic-archive-item");
			if (i != -1) {
				str_temp = str;
				str_temp=str_temp.replaceAll(".*a href=\"","");
				str_temp=str_temp.replaceAll("\".*","");
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
		return "http://www.thedoghousediaries.com/?page_id=1386";
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
			int index1 = str.indexOf("comic-item comic-item");
			if (index1 != -1) {
				final_str = str;
				final_itext = str;
				final_title = str;
			}	}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_title = final_title.replaceAll(".*comic-item comic-item-","");
		final_title = final_title.replaceAll("\".*","");
		final_itext = final_itext.replaceAll(".*title=\"","");
		final_itext = final_itext.replaceAll("\".*","");
		strip.setTitle("Dog House Diaries #"+final_title);
		strip.setText(final_itext);
		return final_str;
	}
}