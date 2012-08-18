package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;



public class Yafgc extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://yafgc.net/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://yafgc.net/";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("Your daily fantasy webcomic");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
    	final_str = final_str.replaceAll(".*comic/","");
    	final_str = final_str.replaceAll(".jpg.*","");
	    return Integer.parseInt(final_str);
	}


	@Override
	public String getStripUrlFromId(int num) {
		return "http://yafgc.net/?id=" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		String temp = url.replaceAll("http.*id=", "");
		return Integer.parseInt(temp);
	}

	@Override
	protected boolean htmlNeeded() {
		return false;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String url_str = url.replaceAll(".*id=", "");
		strip.setTitle("YAFGC: "+url_str);
		strip.setText("-NA-");
		return "http://yafgc.net/img/comic/"+url_str+".jpg"; 
	}
}
