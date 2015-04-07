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
		String image_url = null;
		String str_index = null;
		String str_title = null;
		String str_itext = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("http://imgs.xkcd.com/comics");
			if (index1 != -1) {
				image_url = str.replaceAll(".*http:","http:");
			}
			int index2 = str.indexOf("src=\"//imgs.xkcd.com/comics");
			if (index2 != -1) {
				str_itext = str;
			}

			int index3 = str.indexOf("Permanent link to this comic");
			if (index3 != -1) {
				str_index = str;
			}
			if(str.indexOf("<title>") != -1) {
				str_title = str;
			}
		}
		str_index = str_index.replaceAll(".*.com/","");
		str_index = str_index.replaceAll("/.*","");
		str_title = str_title.replaceAll(".*xkcd: ", "");
		str_title = str_title.replaceAll("</title>.*", "");
		str_itext = str_itext.replaceAll(".*title=\"","");
		str_itext = str_itext.replaceAll("\".*","");
		strip.setTitle("Xkcd: " + str_index + ": " + str_title);
		strip.setText(str_itext);
		return image_url;
	}
}
