package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.blogspot.applications4android.comicreader.comictypes.YearlyArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;

/**
 * Class for GirlswithSlingshots
 */
public class GirlswithSlingshots extends YearlyArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.girlswithslingshots.com/";
	}

	@Override
	protected ArrayList<String> getAllComicUrls(BufferedReader reader, int year)
			throws IOException {
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str,str_temp;
		int i;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("rel=\"bookmark\" title=\"Permanent Link");
			if (i != -1) {
				str_temp = str;
				str_temp=str_temp.replaceAll(".*href=\"","");
				str_temp=str_temp.replaceAll("\".*","");
				m_com.add(str_temp);
			}
		}
		return m_com;
	}

	@Override
	protected int getFirstYear() {
		return 2004;
	}

	@Override
	protected String getArchiveUrl(int year) {
		return "http://www.girlswithslingshots.com/archive/?archive_year="+year;
	}

	@Override
	protected boolean neededReversal() {
		return true;
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
		String final_text = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("girlswithslingshots.com/comics/");
			if (index1 != -1) {
				final_str = str;
				final_title = str;
				final_text = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_title = final_title.replaceAll(".*alt=\"","");
		final_title = final_title.replaceAll("\".*","");
		final_text = final_text.replaceAll(".*title=\"","");
		final_text = final_text.replaceAll("\".*","");
		strip.setTitle(final_title); 
		strip.setText(final_text);
		return final_str;
	}
}
