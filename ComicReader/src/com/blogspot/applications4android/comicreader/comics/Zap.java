package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class Zap extends ArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.zapcomic.com/";
	}

	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		String[] str_temp = null;
		String str;
		int i;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("column dropdown_home");
			if (i != -1) {
				str = str.replaceFirst(".*\">Quick Archive</option><option value=\"http", "");
				str_temp = str.split("value=\"http");
			}
		}
		for (i=0;i<str_temp.length;i++) {
			str_temp[i]=str_temp[i].replaceAll(".*://www.", "http://www.");
			str_temp[i]=str_temp[i].replaceAll("/\">.*", "");
		}
	    int left, right;
	    String temp;
		for (left=0, right=str_temp.length-1; left<right; left++, right--) {
		    temp = str_temp[left]; 
		    str_temp[left]  = str_temp[right]; 
		    str_temp[right] = temp;
		}

		return str_temp;
	}

	@Override
	protected String getArchiveUrl() {
		return "http://www.zapcomic.com/";
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
		String str;
		String final_str = null;
		String final_title = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("comic_object");
			if (index1 != -1) {
				final_str = str;
				final_title = str;
			}
		}
		final_str = final_str.replaceAll(".*img src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_title = final_title.replaceAll(".*title=\"","");
		final_title = final_title.replaceAll("\".*","");
		strip.setTitle("Zap: "+final_title); 
		strip.setText(" -NA- ");
		return final_str;
	}
}
