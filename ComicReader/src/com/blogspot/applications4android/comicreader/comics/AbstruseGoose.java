package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;




public class AbstruseGoose extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://www.abstrusegoose.com/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.abstrusegoose.com/";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws ComicLatestException, IOException {
    	String str;
		String final_str = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("div class=\"post\" id=\"post-");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+getName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		final_str = final_str.replaceAll(".*post-","");
		final_str = final_str.replaceAll("\".*","");
		return Integer.parseInt(final_str);
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.abstrusegoose.com/" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		url = url.replace("http://www.abstrusegoose.com/", "");
		return Integer.parseInt(url);
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
		String final_itext = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("img class=\"aligncenter\" src=");
			if (index1 != -1) {
				final_str = str;
				final_itext = str;
			}
			int index3 = str.indexOf("storytitle");
			if (index3 != -1) {
				final_title = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_str = final_str.replaceAll(" ","%20");
		if(!final_str.contains("abstrusegoose.com")) {
			final_str = "http://www.abstrusegoose.com" + final_str;
    	}
    	final_title = final_title.replaceAll(".*bookmark\">","");
    	final_title = final_title.replaceAll("<.*","");
		if(final_itext.contains("title")) {
			final_itext = final_itext.replaceAll(".*title=\"","");
			final_itext = final_itext.replaceAll("\".*","");
			if (final_itext.equals("")) {
				final_itext = "-NA-";
			}
		} else {
			final_itext = "-NA-";
    	}
		strip.setTitle("AbstruseGoose: " + final_title);
		strip.setText(final_itext);
		return final_str;
	}
}
