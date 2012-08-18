package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;



public class AGirlandHerFed extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://agirlandherfed.com/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://agirlandherfed.com/";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("img/strip/1-");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		final_str = final_str.replaceAll(".*img/strip/1-","");
		final_str = final_str.replaceAll(".jpg.*","");
	   	int finalid = Integer.parseInt(final_str);
	   	return finalid;
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://agirlandherfed.com/1." + num + ".html";
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		String temp = url.replaceAll("http.*/1.", "");
		temp = temp.replaceAll(".html", "");
		return Integer.parseInt(temp);
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
			int index1 = str.indexOf("img/strip/1-");
			if (index1 != -1) {
				final_str = str;
				final_title = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_title = final_title.replaceAll(".*img/strip/1-","");
		final_title = final_title.replaceAll(".jpg.*","");
		strip.setTitle("A Girl and Her Fed: 1."+final_title);
		strip.setText("-NA-");
		return "http://agirlandherfed.com/"+final_str;
	}
}
