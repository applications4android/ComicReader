package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;

public class MegaTokyo extends IndexedComic {

	
	@Override
	protected String getFrontPageUrl() {
		return "http://megatokyo.com/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://megatokyo.com/strip";
	}
	
	@Override
	protected int parseForLatestId(BufferedReader reader) throws ComicLatestException, IOException {
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("<img align=\"middle\" src=\"strips");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
    	final_str = final_str.replaceAll(".*.Strip","");
    	final_str = final_str.trim();
    	final_str = final_str.replaceAll("\".*","");
	    return Integer.parseInt(final_str);
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.megatokyo.com/strip/" + num;	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*com/strip/", ""));
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
			int index1 = str.indexOf("<img align=\"middle\" src=\"strips/");
			if (index1 != -1) {
				final_str = str;
				final_itext = str;
				final_title = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
     	final_title = final_title.replaceAll(".*.Comic","");
    	final_title = final_title.trim();
    	final_title = final_title.replaceAll(",.*","");
    	final_itext = final_itext.replaceAll(".*.;,","");
    	final_itext = final_itext.trim();
    	final_itext = final_itext.replaceAll("\".*","");
		strip.setTitle("MegaTokyo: " + final_title);
		strip.setText(final_itext);
		final_str = getFrontPageUrl() + final_str;
		return final_str; 

	}

}
