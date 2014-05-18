package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;

//import android.util.Log;

public class PhDComics extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://www.phdcomics.com/comics.php";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.phdcomics.com/comics.php";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("javascript:popupwin");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		final_str = final_str.replaceAll(".*comicid=","");
		final_str = final_str.replaceAll("\'.*","");
	   	return Integer.parseInt(final_str);
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.phdcomics.com/comics/archive.php?comicid=" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*comicid=", ""));
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
			int index1 = str.indexOf("image_src");
			if (index1 != -1) {
				final_str = str;
				final_title = str;
			}
		}
//		Log.d("PhDComics", "full string " + final_str);
		final_str = final_str.replaceAll(".*href=\'","");
		final_str = final_str.replaceAll("\'.*","");
		final_title = final_title.replaceAll(".*\"title\" content=\"","");
		final_title = final_title.replaceAll("\".*","");
		strip.setTitle(final_title); 
		strip.setText("-NA-");
    	return final_str;
	}
}
