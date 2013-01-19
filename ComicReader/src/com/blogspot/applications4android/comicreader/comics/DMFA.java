package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;




public class DMFA extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://www.missmab.com/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.missmab.com/";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws ComicLatestException, IOException {
    	String str;
		String final_str = null;
		while ((str = reader.readLine()) != null) {
			if(str.indexOf("Images/comicprev.gif") != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for " + getName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		// using prev-id to get to the latest id.
		final_str = final_str.replaceAll(".*<A HREF=\"Comics/Vol_","");
		final_str = final_str.replaceAll(".php.*", "");
		return (Integer.parseInt(final_str) + 1);
	}

	@Override
	public String getStripUrlFromId(int num) {
		String numStr;
		if(num < 100) {
			numStr = String.format("%03d", num);
		}
		else {
			numStr = "" + num;
		}
		return "http://www.missmab.com/Comics/Vol_" + numStr + ".php";
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		url = url.replace("http://www.missmab.com/Comics/Vol_", "");
		url = url.replace(".php", "");
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
		String final_itext = null;
		while ((str = reader.readLine()) != null) {
			if(str.indexOf("<TITLE>") != -1) {
				final_itext = str;
			}
		}
		final_itext = final_itext.replaceAll(".*<TITLE>", "");
		final_itext = final_itext.replaceAll("</TITLE>.*", "");
		strip.setTitle("DMFA: #" + getIdFromStripUrl(url));
		strip.setText(final_itext);
		return final_str;
	}

	private String getImageUrlFromStripUrl(String url) {
		int id = getIdFromStripUrl(url);
		String idStr;
		if(id < 10) {
			idStr = "0" + id;
		}
		else {
			idStr = "" + id;
		}
		return "http://www.missmab.com/Comics/Vol" + idStr + ".jpg";
	}
}
