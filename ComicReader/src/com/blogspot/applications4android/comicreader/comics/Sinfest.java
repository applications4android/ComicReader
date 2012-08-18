package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;


import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class Sinfest extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://www.sinfest.net/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.sinfest.net";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("images/prev_a.gif");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		final_str = final_str.replaceAll(".*comicID=","");
		final_str = final_str.replaceAll("\".*","");
		int final_temp = Integer.parseInt(final_str) + 1;
		return final_temp;
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.sinfest.net/archive_page.php?comicID=" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*ID=", ""));
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
			int index1 = str.indexOf("http://sinfest.net/comikaze/comics/");
			if (index1 != -1) {
				final_str = str;
				final_title = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
	   	final_title = final_title.replaceAll(".*comics/","");
	   	final_title = final_title.replaceAll(".gif\" alt=\""," - ");
      	final_title = final_title.replaceAll("\".*","");
		strip.setTitle("Sinfest: "+final_title);
		strip.setText("-NA-");
		return final_str;
	}
}
