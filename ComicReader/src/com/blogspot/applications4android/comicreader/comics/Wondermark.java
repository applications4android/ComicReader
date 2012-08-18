package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class Wondermark extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://wondermark.com/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://wondermark.com/";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("og:title");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
    	final_str = final_str.replaceAll(".*content=\"#","");
    	final_str = final_str.replaceAll(";.*","");
	    return Integer.parseInt(final_str);
	}

	@Override
	public String getStripUrlFromId(int num) {
		if (num < 10) {
			return "http://wondermark.com/00" + num;
		} else if (num < 100) {
			return "http://wondermark.com/0" + num;
		} else {
			return "http://wondermark.com/" + num;
		}
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
		String final_text = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("og:image");
			if (index1 != -1) {
				final_str = str;
			}
			int index3 = str.indexOf("og:title");
			if (index3 != -1) {
				final_title = str;
			}
			if(str.indexOf("id=\"comic\"") != -1) {
				final_text = str;
			}
		}
		final_str = final_str.replaceAll(".*content=\"","");
		final_str = final_str.replaceAll("\".*","");
    	final_title = final_title.replaceAll(".*content=\"","");
    	final_title = final_title.replaceAll("\".*","");
    	final_text = final_text.replaceAll(".*alt=\"", "");
    	final_text = final_text.replaceAll("\".*", "");
		strip.setTitle("Wondermark: "+final_title);
		strip.setText(final_text);
		return final_str; 
	}
}
