package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;




public class IrregularWebComic extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://irregularwebcomic.net/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://irregularwebcomic.net/";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws ComicLatestException, IOException {
    	String str;
		String final_str = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("<title>");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+getName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		final_str = final_str.replaceAll(".*#","");
		final_str = final_str.replaceAll("</.*","");
		return Integer.parseInt(final_str);
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://irregularwebcomic.net/" + num + ".html";
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		url = url.replace("http://irregularwebcomic.net/", "");
		url = url.replace(".html", "");
		return Integer.parseInt(url);
	}

	@Override
	protected boolean htmlNeeded() {
		return false;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
		int id = getIdFromStripUrl(url);
		String a;
		if (id <10) {
			a = "000"+ id;
		} else if (id <100) {
			a = "00"+ id;
		} else if (id <1000) {
			a = "0"+ id;
		} else {
			a = ""+id;	
		}
		String final_str = "http://irregularwebcomic.net/comics/irreg" + a + ".jpg";
		strip.setTitle("IrregularWebComic: " + id);
		strip.setText("-NA-");
		return final_str;
	}
}
