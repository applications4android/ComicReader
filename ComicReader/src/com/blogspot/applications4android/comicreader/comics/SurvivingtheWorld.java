package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;



public class SurvivingtheWorld extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://survivingtheworld.net/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://survivingtheworld.net/";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("img src=\"Lesson");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		final_str = final_str.replaceAll(".*Lesson","");
    	final_str = final_str.replaceAll(".jpg.*","");
	    return Integer.parseInt(final_str);
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://survivingtheworld.net/Lesson" + num+ ".html";
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*Lesson", "").replaceAll(".html", ""));
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
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("img src=\"Lesson");
			if (index1 != -1) {
				final_str = str;
			}
			int index2 = str.indexOf("Lesson #");
			if (index2 != -1) {
				final_title = str;
			}
		}
		final_str = "http://survivingtheworld.net/"+final_str.replaceAll(".*img src=\"","");
		final_str = final_str.replaceAll("\".*","");
    	final_title = final_title.replaceAll(".*Lesson","Lesson");
    	final_title = final_title.replaceAll("</span>.*","");
		strip.setTitle("Surviving the World: "+final_title);
		strip.setText("-NA-");
		return final_str;
	}
}
