package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.blogspot.applications4android.comicreader.comictypes.YearlyArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class LeastICouldDo extends YearlyArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.leasticoulddo.com/";
	}

	@Override
	protected ArrayList<String> getAllComicUrls(BufferedReader reader, int year)
			throws IOException {
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str,str_temp;
		int i;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("/comic/20");
			if (i != -1) {
				str_temp = str;
				str_temp=str_temp.replaceAll(".*href=\"","");
				str_temp=str_temp.replaceAll("\".*","");
				str_temp = "http://leasticoulddo.com"+str_temp;
				m_com.add(str_temp);
			}
		}
		return m_com;
	}

	@Override
	protected int getFirstYear() {
		return 2003;
	}

	@Override
	protected String getArchiveUrl(int year) {
		return "http://leasticoulddo.com/archive/calendar/"+year;
	}

	@Override
	protected boolean neededReversal() {
		return false;
	}

	@Override
	protected boolean htmlNeeded() {
		return false;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String date = url.replaceAll(".*comic/", "");
        strip.setTitle("Least I Could Do: " + date);
		strip.setText("-NA-");
		return "http://cdn.leasticoulddo.com/comics/" + date + ".gif";
	}
}
