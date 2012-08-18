package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.JSONObject;

import android.util.Log;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Bound;
import com.blogspot.applications4android.comicreader.core.Downloader;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class GeekAndPoke extends IndexedComic {
	private HashMap<Integer, String> mAllUrls;
	private static final String mUid = "6a00d8341d3df553ef00e5507ebb188834";
	private static final String TAG = "GeekAndPoke";
	private int mTotal;


	public GeekAndPoke() {
		super();
		mAllUrls = new HashMap<Integer, String>();
		mTotal = -1;
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://geekandpoke.typepad.com/geekandpoke";
	}

	private String getURLFromAPI(int actualId) {
		try {
			String query = "http://api.typepad.com/blogs/"+mUid+"/post-assets.json?max-results=1&start-index="+actualId;
			Log.d(TAG, "query="+query);
			URI url = new URI(query);
			String str = Downloader.downloadToString(url);
			JSONObject root = new JSONObject(str);
			if(mLatestId < 1) {
				mLatestId = root.getInt("totalResults");
			}
			if(mTotal < 0) {
				mTotal = root.getInt("totalResults");
			}
			JSONObject entry = (JSONObject) root.getJSONArray("entries").get(0);
			String curl = (String) entry.get("permalinkUrl");
			return curl;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected String getLatestStripUrl() {
		if(mLatestId < 1) {
			String url = getURLFromAPI(1);
			mLatestId = mTotal;
			mAllUrls.put(mLatestId, url);
		}
		mBound = new Bound((long) getFirstId(), (long) mLatestId);
		return getStripUrlFromId(mLatestId);
	}

	@Override
    protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
		String str;
		String str1 = null;
		String str2 = null;
		String final_str = null;
		String final_title = null;
		boolean entryBodyFound = false;
		while((str = reader.readLine()) != null) {
			int index2 = str.indexOf("<title>");
			if (index2 != -1) {
				final_title = str;
			}
			if(!entryBodyFound) {
				if(str.indexOf("entry-body") >= 0) {
					entryBodyFound = true;
				}
				continue;
			}
			int index1 = str.indexOf("src=\"http://geekandpoke.typepad.com/.a");
			if (index1 != -1) {
				str1 = str;
			}
			int index3 = str.indexOf("src=\"http://geekandpoke.typepad.com/geekandpoke/");
			if (index3 != -1) {
				str2 = str;
			}
		}
		if (str1 == null) {
			final_str = str2;
		} else if (str2 == null) {
			final_str = str1;
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_title = final_title.replaceAll(".*<title>","");
		final_title = final_title.replaceAll("</title>.*","");
		strip.setTitle(final_title); 
		strip.setText("-NA-");
		return final_str;
	}

	@Override
	protected int parseForLatestId(BufferedReader reader)
			throws ComicLatestException, IOException {
		return 0;
	}

	@Override
	public String getStripUrlFromId(int num) {
		if(!mAllUrls.containsKey(num)) {
			int actualId = mLatestId - num + 1;
			String url = getURLFromAPI(actualId);
			mAllUrls.put(num, url);
		}
		return mAllUrls.get(num);
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		Iterator<Entry<Integer, String>> itr = mAllUrls.entrySet().iterator();
		while(itr.hasNext()) {
			Entry<Integer, String> e = itr.next();
			if(e.getValue().equals(url)) {
				return e.getKey();
			}
		}
		return mLatestId;
	}

	@Override
	protected String getFrontPageUrl() {
		return null;
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}
}
