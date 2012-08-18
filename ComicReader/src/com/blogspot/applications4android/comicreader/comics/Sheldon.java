package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.YearlyArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class Sheldon extends YearlyArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.sheldoncomics.com/";
	}

	@Override
	protected ArrayList<String> getAllComicUrls(BufferedReader reader, int year)
			throws IOException {
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str,str_temp;
		int i;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("<td><a href=\"/archive/");
			if (i != -1) {
				str_temp = str;
				str_temp=str_temp.replaceAll(".*href=\"","");
				str_temp=str_temp.replaceAll("\".*","");
				str_temp="http://www.sheldoncomics.com"+str_temp;
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
		int curr = Calendar.getInstance().get(Calendar.YEAR);
		if(curr == year) {
			return "http://www.sheldoncomics.com/archive/archives.html";
		}
		return "http://www.sheldoncomics.com/archive/"+year+".html";
	}

	@Override
	protected boolean neededReversal() {
		return false;
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
			int index1 = str.indexOf("img src=\"/strips");
			if (index1 != -1) {
				final_str = str;
				final_title = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_title = final_title.replaceAll(".*title=\"strip for ","");
		final_title = final_title.replaceAll("\".*","");
		strip.setTitle("Sheldon: "+final_title); 
		strip.setText(" -NA- ");
		return "http://www.sheldoncomics.com"+final_str;
	}
}
