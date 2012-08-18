package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class VGCats extends IndexedComic {
	
	@Override
	protected String getFrontPageUrl() {
		return "http://www.vgcats.com/comics/?";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.vgcats.com/comics/?";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("back.gif");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
    	final_str = final_str.replaceAll(".*strip_id=","");
    	final_str = final_str.replaceAll("\".*","");
	    return Integer.parseInt(final_str) + 1;
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.vgcats.com/comics/?strip_id=" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*id=", ""));
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
			int index1 = str.indexOf("src=\"images/");
			if (index1 != -1) {
				final_str = str;
				final_title = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_str = "http://www.vgcats.com/comics/"+final_str;
    	final_title = final_title.replaceAll(".*images/","");
    	final_title = final_title.replaceAll(".jpg.*","");
    	final_title = final_title.replaceAll(".gif.*","");
		strip.setTitle("VGCats: "+final_title);
		strip.setText("-NA-");
		return final_str;
	}
}
