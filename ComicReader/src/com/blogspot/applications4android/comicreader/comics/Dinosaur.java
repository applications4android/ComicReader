package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class Dinosaur extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://www.qwantz.com/index.php?mobile=0";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.qwantz.com";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("transcribe");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		final_str = final_str.replaceAll(".*comic=","");
		final_str = final_str.replaceAll("\'.*","");
	   	return Integer.parseInt(final_str);
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.qwantz.com/index.php?mobile=0&comic=" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*comic=", ""));
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
		String final_date = null;	
		String final_itext = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("class=\"comic\"");
			if (index1 != -1) {
				final_str = str;
				final_itext = str;
			}
			int index3 = str.indexOf("title>Dinosaur Comics");
			if (index3 != -1) {
				final_date = str;
			}
		}
		final_str = final_str.replaceAll(".*<img src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_itext = final_itext.replaceAll(".*class=\"comic\" title=\"","");
		final_itext = final_itext.replaceAll("\".*","");
		final_date = final_date.replaceAll(".*<title>","");
		final_date = final_date.replaceAll(" - awesome.*","");
		final_title=final_date;
		strip.setTitle(final_title); 
		strip.setText(final_itext);
		return final_str;
	}

}
