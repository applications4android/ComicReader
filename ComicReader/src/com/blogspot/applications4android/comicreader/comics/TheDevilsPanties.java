package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class TheDevilsPanties extends IndexedComic {
	
	@Override
	protected String getFrontPageUrl() {
		return "http://thedevilspanties.com/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://thedevilspanties.com/";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("comic-id-");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
    	final_str = final_str.replaceAll(".*comic-id-","");
    	final_str = final_str.replaceAll("\".*","");
	    return Integer.parseInt(final_str);
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://thedevilspanties.com/archives/" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*archives/", ""));
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
			int index1 = str.indexOf("comicpane");
			if (index1 != -1) {
				final_str = str;
				final_title = str;
			}
		}
		/*
		if((final_str == null) || (final_title == null)) {
			String msg = "Failed to find the comic for URL="+url;
			ComicNotFoundException cnf = new ComicNotFoundException(msg);
			throw cnf;
		}
		*/
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
    	final_title = final_title.replaceAll(".*alt=\"","");
    	final_title = final_title.replaceAll("\".*","");
		strip.setTitle("The Devils Panties: "+final_title);
		strip.setText("-NA-");
		return final_str; 
	}
	
	@Override
	protected int getFirstId() {
		return 300;
	}
}
