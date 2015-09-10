package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
//import android.util.Log;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class SMBC extends IndexedComic {
	private final int[] mExceptions;
	private final HashMap<Integer, Integer> mHash;


	public SMBC() {
		super();
		mExceptions = new int[] {  4,  11,  15,  16,  17,  18,  22,  24,  36,  39,  43,  46, 47,
								   52,  53,  59,  61,  62,  65,  67,  79,  90,  92,  98,  99, 101,
								   103, 106, 109, 110, 116, 118, 121, 126, 127, 128, 129, 141, 261,
								   426, 427,1967,2025 };
		mHash = new HashMap<Integer, Integer>(mExceptions.length);
		for(int id : mExceptions) {
			mHash.put(id, 1);
		}
	}

	@Override
	protected String getFrontPageUrl() {
		return "http://www.smbc-comics.com/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.smbc-comics.com/";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		boolean found = false;
		while((str = reader.readLine()) != null) {
			if(found) continue;
			int index1 = str.indexOf("twittershare");
			if (index1 != -1) {
				final_str = str;
				found = true;
			}
		}
//Log.d("SMBC", "i final_string " + final_str);
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		final_str = final_str.replaceAll("&text=.*","");
		final_str = final_str.replaceAll(".*id=","");
//Log.d("SMBC", "i final_string " + final_str);
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
		return "http://www.smbc-comics.com/?id=" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*id=", ""));
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String str1;
		String final_str = null;
		String image_url = null;
		String final_title = null;
		String final_index = null;
		while ((str1 = reader.readLine()) != null) {
			int index1 = str1.indexOf("comics/");
			if ( (index1 != -1) && (final_str == null) ) {
				final_str = str1;
			}
		}
//Log.d("SMBC", "final_string " + final_str);
		image_url = final_str.replaceAll(".*src=\"","http://www.smbc-comics.com/");
		image_url = image_url.replaceAll("\".*","");
//Log.d("SMBC", "image_url " + image_url);
		final_title = final_str.replaceAll(".*title=\"","");
		final_title = final_title.replaceAll("\".*","");
//Log.d("SMBC", "final_title " + final_title);
		final_index=url.replaceAll(".*=","");
//Log.d("SMBC", "final_index " + final_index);
		strip.setTitle("SMBC: "+final_index+": "+final_title);
		strip.setText("-NA-");
		return image_url;
	}

}
