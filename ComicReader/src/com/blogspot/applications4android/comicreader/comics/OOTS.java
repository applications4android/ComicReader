package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class OOTS extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://www.giantitp.com/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.giantitp.com";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("A href=\"/comics/oots");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		final_str = final_str.replaceAll(".*oots","");
		final_str = final_str.replaceAll(".html.*","");
	   	int finalid = Integer.parseInt(final_str);
	   	return finalid;
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.giantitp.com/comics/oots" + String.format("%04d", num) + ".html";
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*oots", "").replaceAll(".html", ""));
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
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("align=\"center\"><IMG src=\"");
			if (index1 != -1) {
				final_str = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		String strip_t = url;
		strip_t = strip_t.substring(strip_t.length()-9);
		strip.setTitle("Order of the Stick: #" + strip_t.replaceAll(".html", ""));
    	strip.setText("-NA-");
		return "http://www.giantitp.com"+final_str;
	}
}
