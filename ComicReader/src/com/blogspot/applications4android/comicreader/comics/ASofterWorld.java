package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class ASofterWorld extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://www.asofterworld.com/";
	}

	public String getComicWebPageUrl() {
		return "http://www.asofterworld.com/";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("<title>");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
    	final_str = final_str.replaceAll(".*World: ","");
    	final_str = final_str.replaceAll("</title>.*","");
	    return Integer.parseInt(final_str);
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.asofterworld.com/index.php?id=" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		String temp = url.replaceAll("http.*id=", "");
		return Integer.parseInt(temp);
	}

	@Override
	protected int getFirstId() {
		return 1;
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
		String final_itext = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("onclick=\"makeAlert");
			if (index1 != -1) {
				final_str = str;
				final_itext = str;
			}
			int index3 = str.indexOf("<title>");
			if (index3 != -1) {
				final_title = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
    	final_title = final_title.replaceAll(".*<title>","");
    	final_title = final_title.replaceAll("</title>.*","");
		final_itext = final_itext.replaceAll(".*title=\"","");
		final_itext = final_itext.replaceAll("\".*","");
		strip.setTitle(final_title);
		strip.setText(final_itext);
		if(!final_str.startsWith("http://www.asofterworld.com")) {
			return "http://www.asofterworld.com/"+final_str;
		}
		return final_str;
	}
}
