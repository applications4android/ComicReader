package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.blogspot.applications4android.comicreader.comictypes.YearlyArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class WapsiSquare extends YearlyArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://wapsisquare.com/";
	}

	@Override
	protected ArrayList<String> getAllComicUrls(BufferedReader reader, int year)
			throws IOException {
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str, str_temp;
		int i, j;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("Permanent Link:");
			j = str.indexOf("comic");
			if (i != -1 && j != -1) {
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
		return 2001;
	}

	@Override
	protected String getArchiveUrl(int year) {
		return "http://wapsisquare.com/archive/?archive_year=" + year;
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
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("comicpane");
			if (index1 != -1) {
				final_str = str;
				final_title = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_title = final_title.replaceAll(".*title=\"","");
		final_title = final_title.replaceAll("\".*","");
		strip.setTitle("Wapsi Square: "+final_title); 
		strip.setText("-NA-");
		return final_str;
	}
}
