package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;
//import android.util.Log;


public class ExtraOrdinary extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://www.exocomics.com";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.exocomics.com";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("og:url");
			if (index1 != -1) {
				final_str = str;
				break;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		final_str = final_str.replaceAll(".*content=\"http://www.exocomics.com/","");
		final_str = final_str.replaceAll("\".*","");
//Log.d("ExtraOrdinary", "final_string " + final_str);
	   	return Integer.parseInt(final_str);
	}

	@Override
	public String getStripUrlFromId(int num) {
		String str_url;
		if ( num<10 ) {
		   str_url="http://www.exocomics.com/0" + num;
		} else {
		   str_url="http://www.exocomics.com/" + num;
		}
		return str_url;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*exocomics.com/", ""));
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String str;
		String image_url = null;
		String final_id = null;
		String final_text = null;
		int ifound=0;
		while ((str = reader.readLine()) != null) {
			if ( ifound==0 && str.indexOf("style-main-comic") != -1) {
			    ifound=1;
			}
			if (ifound != 0 ) {
			    if (str.indexOf("src=") != -1) {
				image_url= str;
			    } else if ( str.indexOf("alt=") != -1) {
				final_id = str;
			    } else if ( str.indexOf("title=") != -1) {
				final_text = str;
			    } else if (str.indexOf("/>") != -1) {
				break;
			    }
			}
		}
//Log.d("ExtraOrdinary", "final_text " + final_text);
		final_text=final_text.replaceAll(".*\"(.*)\".*","$1");
		strip.setText(final_text);
		final_id=final_id.replaceAll(".*\"(.*)\".*","$1");
		String final_title = "Extra Ordinary: "+final_id;
		strip.setTitle(final_title);
		image_url=image_url.replaceAll(".*\"(.*)\".*","$1");
//Log.d("ExtraOrdinary", "image_url " + image_url);
    	return image_url;
	}
}
