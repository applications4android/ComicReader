package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;




public class PbfComics extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://www.pbfcomics.com";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.pbfcomics.com";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws ComicLatestException, IOException {
    	String str;
		String final_str = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("Roxanne");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+getName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		int loc = final_str.indexOf("name=\"");
		if(loc == -1) {
			String msg = "Failed to get the latest id for "+getName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		final_str = final_str.substring(loc+6, loc+10);
		final_str = final_str.replaceAll("\".*", "");
		return Integer.parseInt(final_str);
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.pbfcomics.com/" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		url = url.replace("http://www.pbfcomics.com/", "");
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
		String final_title = "PBF: " + getIdFromStripUrl(url);
		String final_itext = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("topimg");
			if (index1 != -1) {
				final_str = str;
				final_itext = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		if(!final_str.contains("pbfcomics.com")) {
			final_str = "http://www.pbfcomics.com/" + final_str;
    	}
		final_itext = final_itext.replaceAll(".*alt=\"", "");
		final_itext = final_itext.replaceAll("\".*", "");
		strip.setTitle(final_title);
		strip.setText(final_itext);
		return final_str;
	}
}
