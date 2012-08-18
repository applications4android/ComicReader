package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.YearlyArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;

/**
 * Class for something positive
 */
public class SomethingPositive extends YearlyArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.somethingpositive.net/";
	}

	@Override
	protected ArrayList<String> getAllComicUrls(BufferedReader reader, int year)
			throws IOException {
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str,str_temp;
		int i, j;
		String ys = Integer.toString(year);
		while((str = reader.readLine()) != null) {
			i = str.indexOf("<a href=\"sp");
			j = str.indexOf(ys);
			if (i != -1 && j != -1) {
				str_temp = str;
				str_temp=str_temp.replaceAll(".*href=\"","");
				str_temp=str_temp.replaceAll("\".*","");
				str_temp="http://www.somethingpositive.net/"+str_temp;
				m_com.add(str_temp);
			}
		}
		return m_com;
	}

	@Override
	protected int getFirstYear() {
		return 2002;
	}

	@Override
	protected String getArchiveUrl(int year) {
		int curr = Calendar.getInstance().get(Calendar.YEAR);
		if(curr == year) {
			return "http://www.somethingpositive.net/archive.shtml";
		}
		else if(curr == getFirstYear()) {
			return "http://www.somethingpositive.net/20012002archive.shtml";
		}
		else {
			return "http://www.somethingpositive.net/"+year+"archive.shtml";
		}
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
		int done = 0;
		String search = url;
		search = search.substring(search.length()-14, search.length()-6);
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf(search);
			int index2 = str.indexOf("src=\"/arch/");
			if ((index1 != -1 || index2 != -1) && done == 0) {
				final_str = str;
				final_title = str;
				done = 1;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_str = "http://www.somethingpositive.net/"+final_str;
		final_title = final_title.replaceAll(".*src=\"","");
		final_title = final_title.replaceAll(".*images/","");
		final_title = final_title.replaceAll("\".*","").replaceAll(".png", "").replaceAll(".gif", "");
		strip.setTitle("Something Positive: "+final_title); 
		strip.setText(" -NA- ");
		return final_str;
	}
}
