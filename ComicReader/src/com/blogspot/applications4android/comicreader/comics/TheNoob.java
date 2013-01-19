package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;




public class TheNoob extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://www.thenoobcomic.com/index.php";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.thenoobcomic.com";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws ComicLatestException, IOException {
    	String str;
		String final_str = null;
		while ((str = reader.readLine()) != null) {
			if(str.indexOf("comic_nav_previous_button") != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for " + getName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		// using prev-id to get to the latest id.
		final_str = final_str.replaceAll(".*href=\"\\?pos=","");
		final_str = final_str.replaceAll("\".*", "");
		return (Integer.parseInt(final_str) + 1);
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.thenoobcomic.com/index.php?pos=" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		url = url.replace("http://www.thenoobcomic.com/index.php?pos=", "");
		return Integer.parseInt(url);
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
    	String str;
		String final_str = getImageUrlFromStripUrl(url);
		String stripId = "<p>" + getIdFromStripUrl(url) + ":";
		String final_itext = null;
		while ((str = reader.readLine()) != null) {
			if(str.indexOf(stripId) != -1) {
				final_itext = str;
			}
		}
		final_itext = final_itext.replaceAll(".*<p>", "");
		final_itext = final_itext.replaceAll("</p>.*", "");
		strip.setTitle("The Noob: " + getIdFromStripUrl(url));
		strip.setText(final_itext);
		return final_str;
	}

	private String getImageUrlFromStripUrl(String url) {
		int num = getIdFromStripUrl(url);
		return "http://www.thenoobcomic.com/headquarters/comics/" + String.format("%05d", num) + ".jpg";
	}
}
