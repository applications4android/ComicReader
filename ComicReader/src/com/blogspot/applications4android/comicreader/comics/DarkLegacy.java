package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;



public class DarkLegacy extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://www.darklegacycomics.com/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.darklegacycomics.com/";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("var iLatestComic");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		final_str = final_str.replaceAll("var iLatestComic=","");
		final_str = final_str.replaceAll(";.*","");
		return Integer.parseInt(final_str);
   	}

	@Override
	public int addException(int in, int increment) {
		if((in == 186) || (in == 209)) {
			in += increment;
		}
		return in;
	}

	@Override
	public String getStripUrlFromId(int num) {
		String str;
		if(num == 1) {
			str = "first";
		} else {
			str = "" + num;
		}
		return "http://www.darklegacycomics.com/" + str + ".html";
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		String str = url.replaceAll("http.*com/", "").replaceAll(".html", "");
		if(str.equals("first")) {
			return 1;
		}
		return Integer.parseInt(str);
	}

	@Override
	protected boolean htmlNeeded() {
		return false;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		int id = getIdFromStripUrl(url);
		strip.setTitle("Dark Legacy: "+id); 
		strip.setText("-NA-");
		return "http://www.darklegacycomics.com/"+id+".jpg";
	}
}
