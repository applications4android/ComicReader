package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.blogspot.applications4android.comicreader.comictypes.YearlyArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class DumbingofAge extends YearlyArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.dumbingofage.com/";
	}

	@Override
	protected ArrayList<String> getAllComicUrls(BufferedReader reader, int year) throws IOException {
		ArrayList<String> coms = new ArrayList<String>(); 
		String str;
		while((str = reader.readLine()) != null) {
			if(str.indexOf("http://www.dumbingofage.com/"+year+"/comic/") != -1) {
				String str_temp = str.replaceAll(".*href=\"","");
				str_temp = str_temp.replaceAll("\".*","");
				coms.add(str_temp);
			}
		}
		return coms;
	}

	@Override
	protected int getFirstYear() {
		return 2010;
	}

	@Override
    protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
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
		strip.setTitle("Dumbing of Age: "+final_title); 
		strip.setText("-NA-");
		return final_str;
    }

	@Override
	protected String getArchiveUrl(int year) {
		return "http://www.dumbingofage.com/archive/?archive_year="+year;
	}

	@Override
	protected boolean neededReversal() {
		return false;
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}
}
