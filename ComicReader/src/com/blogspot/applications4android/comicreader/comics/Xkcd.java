package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class Xkcd extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://www.xkcd.com/";
	}

	public String getComicWebPageUrl() {
		return "http://www.xkcd.com";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("Permanent link to this comic");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
    	final_str = final_str.replaceAll(".*.com/","");
    	final_str = final_str.replaceAll("/.*","");
	    return Integer.parseInt(final_str);
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.xkcd.com/" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*com/", ""));
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
		String final_title1 = null;
		String final_itext = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("<img src=\"http://imgs.xkcd.com/comics");
			if (index1 != -1) {
				final_str = str;
				final_itext = str;
			}
			int index3 = str.indexOf("Permanent link to this comic");
			if (index3 != -1) {
				final_title = str;
			}
			if(str.indexOf("<title>") != -1) {
				final_title1 = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
    	final_title = final_title.replaceAll(".*.com/","");
    	final_title = final_title.replaceAll("/.*","");
    	final_title1 = final_title1.replaceAll(".*<title>", "");
    	final_title1 = final_title1.replaceAll("xkcd: ", "");
    	final_title1 = final_title1.replaceAll("</title>.*", "");
		final_itext = final_itext.replaceAll(".*title=\"","");
		final_itext = final_itext.replaceAll("\".*","");
		strip.setTitle("Xkcd: " + final_title1 + " : " + final_title);
		strip.setText(final_itext);
		return final_str; 
	}
}
