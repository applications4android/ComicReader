package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class Dieselsweeties extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://www.dieselsweeties.com/";
	}

	public String getComicWebPageUrl() {
		return "http://www.dieselsweeties.com";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("img src=\"/h");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
    	final_str = final_str.replaceAll(".*/\\d","");
    	final_str = final_str.replaceAll("\\.png.*","");
	    return Integer.parseInt(final_str);
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.dieselsweeties.com/archive/" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*com/archive/", ""));
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
		String final_itext = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("<img t");
			if (index1 != -1) {
				final_str = str;
				final_itext = str;
			}

		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_str = "http://www.dieselsweeties.com" + final_str;
		final_itext = final_itext.replaceAll(".*title=\"","");
		final_itext = final_itext.replaceAll("\" .*","");
		strip.setTitle("Diesel Sweeties: " + final_itext);
	
		return final_str; 
	}
}
