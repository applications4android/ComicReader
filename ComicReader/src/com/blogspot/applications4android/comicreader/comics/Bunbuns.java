package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class Bunbuns extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://www.illumatie.nl/bunbuns/viewer.php";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.illumatie.nl/bunbuns/viewer.php";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("alt=\"Latest\"");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		final_str = final_str.replaceAll(".*a href=\"/bunbuns/viewer.php\\?nr=","");
		final_str = final_str.replaceAll("\".*","");
	   	int finalid = Integer.parseInt(final_str);
	   	return finalid;
	}


	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.illumatie.nl/bunbuns/viewer.php?nr=" + String.format("%03d", num);
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*nr=", ""));
	}

	@Override
	protected boolean htmlNeeded() {
		return false;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
		String url_str = url;
		int currId = Integer.parseInt(url_str.replaceAll("http://www.illumatie.nl/bunbuns/viewer.php\\?nr=", ""));
		strip.setTitle("Bunbun: " + String.format("%03d", currId));
		strip.setText(" -NA- ");
		return "http://www.illumatie.nl/bunbuns/pic/images/comics/bunbun-comic"+
				String.format("%03d", currId) + ".gif";
	}
}
