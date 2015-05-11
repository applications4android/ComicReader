package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
//import android.util.Log;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class Yafgc extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://yafgc.net/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://yafgc.net/";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {

//Log.d("YAFGC", "In parseForLatestId");
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("/uploads/");
			int index2 = str.indexOf("title=");
			if (index1 != -1 && index2 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
//Log.d("YAFGC", "final_str=" + final_str);
	    final_str = final_str.replaceAll(".jpg\".*","");
	    final_str = final_str.replaceAll(".*/.{11}","");
	    final_str = final_str.replaceAll("-.*","");
//Log.d("YAFGC", "final_str=" + final_str);
	    return Integer.parseInt(final_str);
	}


	@Override
	public String getStripUrlFromId(int num) {
// Just tacking the number and a minus sign on the end of the URL seems
// to be enough for the server to figure out the full name...
		return "http://yafgc.net/comic/" + num + "-";
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		String temp = url.replaceAll(".*comic/", "");
		temp = temp.replaceAll("-.*", "");
//Log.d("YAFGC", "temp=" + temp);
		return Integer.parseInt(temp);
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {

//Log.d("YAFGC", ":::in parse:::");
		String str;
		String final_str = null;
		String image_url = null;
		String str_title = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf(".jpg");
			int index2 = str.indexOf("img src=\"");
			if (index1 != -1 && index2 != -1 ) {
				final_str = str;
			}
		}
//Log.d("YAFGC", "final_str=" + final_str);
		image_url = final_str.replaceAll(".*img src=\"","");
		image_url = image_url.replaceAll("\".*","");
//Log.d("YAFGC", "image_url=" + image_url);
		str_title = final_str.replaceAll(".*title=\"", "");
		str_title = str_title.replaceAll("\".*", "");
		strip.setTitle("YAFGC: " + str_title);
		strip.setText("");
		return image_url;
	}
}
