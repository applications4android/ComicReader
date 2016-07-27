package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
//import android.util.Log;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


/**
 * Class for GirlswithSlingshots
 */
public class GirlswithSlingshots extends IndexedComic {
	private final int[] mExceptions;
	private final HashMap<Integer, Integer> mHash;

	public GirlswithSlingshots() {
		super();
// These are the missing comics (checked up to #100 so far).
		mExceptions = new int[] {
	 15, 96
		};
		mHash = new HashMap<Integer, Integer>(mExceptions.length);
		for(int id : mExceptions) {
			mHash.put(id, 1);
		}
	}

	@Override
	protected String getFrontPageUrl() {
		return "http://www.girlswithslingshots.com/";
	}

	public String getComicWebPageUrl() {
		return "http://www.girlswithslingshots.com/";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("<title>");
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
    	final_str = final_str.replaceAll(".*GWS","");
    	final_str = final_str.replaceAll("</.*","");
// Delete string that only appears in newer comics.
	final_str = final_str.replaceAll(" Chaser #","");
	return Integer.parseInt(final_str);
	}

	@Override
	public int addException(int id, int increment) {
		while(true) {
			if(!mHash.containsKey(id)) {
				break;
			}
			id += increment;
		}
 		return id;
	}

	@Override
	public String getStripUrlFromId(int num) {
//Log.d("GWS", "num " + num);
		return "http://www.girlswithslingshots.com/comic/gws-chaser-" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
//Log.d("GWS", "url " + url);
		String str_index;
		str_index = url.replaceAll(".*-","");
		return Integer.parseInt(str_index);
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
		String str_itext = null;
		String image_url = null;
		String str_index = null;
		while ((str = reader.readLine()) != null) {
			int index = str.indexOf("cc-comicbody");
			if ( (index != -1) ) {
				final_str = str;
				break;
			}
		}
		final_str = final_str.replaceAll(".*title=\"","");
		str_itext = final_str.replaceAll("\".*", "");
		image_url = final_str.replaceAll(".*src=\"","");
		image_url = image_url.replaceAll("\".*", "");
		str_index = image_url.replaceAll(".*GWS","");
		str_index = str_index.replaceAll(".jpg","");
		strip.setTitle("Girls with Slingshots: " + str_index);
		strip.setText(str_itext);
		return image_url;
	}
}
