package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class Misfile extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://www.misfile.com/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.misfile.com";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("overlay.php");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
    	final_str = final_str.replaceAll(".*Called=","");
    	final_str = final_str.replaceAll("\".*","");
	    return Integer.parseInt(final_str);
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.misfile.com/?page=" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*page=", ""));
	}

	@Override
	protected boolean htmlNeeded() {
		return false;
	}
	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String index = url.replaceAll(".*page=", "");
		String url_str = "http://www.misfile.com/overlay.php?pageCalled="+index;
		strip.setTitle("Misfile: " + index);
		strip.setText("-NA-");
		return url_str;
	}
}
