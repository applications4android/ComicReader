package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class GunnerkriggCourt extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://www.gunnerkrigg.com/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.gunnerkrigg.com/";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("/comics/");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
    	final_str = final_str.replaceAll(".*comics/","");
    	final_str = final_str.replaceAll(".jpg.*","");
	    return Integer.parseInt(final_str);
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.gunnerkrigg.com/archive_page.php?comicID=" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*comicID=", ""));
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
			int index1 = str.indexOf("/comics/");
			if (index1 != -1) {
				final_str = str;
				break;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
    	strip.setTitle("Gunnerkrigg Court");
		strip.setText("-NA-");
		return "http://www.gunnerkrigg.com" + final_str;
	}
}
