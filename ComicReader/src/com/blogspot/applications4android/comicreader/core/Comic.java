package com.blogspot.applications4android.comicreader.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.blogspot.applications4android.comicreader.exceptions.ComicException;
import com.blogspot.applications4android.comicreader.exceptions.ComicSDCardFull;

/**
 * Base class for all comic series
 */
// TODO: add unit-tests
public abstract class Comic extends ComicParser {
	/** properties folder name */
	public static final String PROPS = "props";

	/** previous session */
	public static final int TYPE_PREV_SESSION = 1;
	/** latest comics */
	public static final int TYPE_LATEST = 2;
	/** favorite comics */
	public static final int TYPE_FAVORITE = 3;
	/** preview comics */
	public static final int TYPE_PREVIEW = 4;
	/** background caching */
	public static final int TYPE_CACHING = 5;
	/** unread comics */
	public static final int TYPE_UNREAD = 6;

	/** navigate to latest strip */
	public static final int NAV_LATEST = 11;
	/** navigate to latest strip forcefully */
	public static final int NAV_LATEST_FORCE = 12;
	/** navigate to first strip */
	public static final int NAV_FIRST = 13;
	/** navigate to next strip */
	public static final int NAV_NEXT = 14;
	/** navigate to previous strip */
	public static final int NAV_PREVIOUS = 15;
	/** navigate to random strip */
	public static final int NAV_RANDOM = 16;
	/** navigate to current strip */
	public static final int NAV_CURRENT = 17;
	/** navigate to previous session */
	public static final int NAV_PREV_SESSION = 18;

	/** use date picker dialog */
	public static final int DIALOG_DATE = 51;
	/** use number picker dialog */
	public static final int DIALOG_NUMBER = 52;

	/** for logging purposes only */
	private static final String TAG = "Comic";

	/** uid for latest comic */
	protected String mLatestUid;
	/** uid for first comic */
	protected String mFirstUid;
	/** uid for the previous session comic */
	protected String mPrevSessionUid;

	/** name of this comic */
	private String mName;
	/** english name of this comic */
	private String mComicName;
	/** list of all strips */
	private HashMap<String, Strip> mStrips;
	/** cache manager for this comic */
	private Cache mCache;
	/** default value of zoom */
	private float mDefZoom;
	/** current strip */
	private Strip mCurrent;
	/** launch type */
	private int mType;
	/** favorites array */
	private ArrayList<String> mFavs;
	/** favorites array */
	private ArrayList<String> mUnr;
	/** for traversing on favorites array Also a hack to all pass by reference */
	private int[] mFavIdx;
	/** for traversing on unread array Also a hack to all pass by reference*/
	private int[] mUnrIdx;
	/** background caching enabled */
	private boolean mCacheEnabled;

	/**
	 * Constructor
	 */
	public Comic() {
		mName = getClass().getSimpleName();
		mComicName = null;
		mCache = new Cache(mName);
		mLatestUid = null;
		mFirstUid = null;
		mPrevSessionUid = null;
		mCurrent = null;
		mDefZoom = 1.0f;
		mStrips = new HashMap<String, Strip>();
		mType = TYPE_LATEST;
		mFavs = new ArrayList<String>();
		mUnr = new ArrayList<String>();
		mFavIdx = new int[] {0};
		mUnrIdx = new int[] {0};	
		mCacheEnabled = true;
	}

	/**
	 * Tell to this comic whether caching is enabled or not. This'll be used to
	 * decide whether to forcefully download the latest comic or not.
	 * 
	 * @param cache
	 *            true if it is, else false
	 */
	public void setCacheEnabled(boolean cache) {
		mCacheEnabled = cache;
	}

	/**
	 * Set the launch type for this comic
	 * 
	 * @param type
	 *            launch type
	 */
	public void setLaunchType(int type) {
		mType = type;
	}

	/**
	 * get the launch type
	 * 
	 * @return launch type
	 */
	public int getLaunchType() {
		return mType;
	}

	/**
	 * Get the comic name
	 * 
	 * @return name
	 */
	public String getName() {
		return mName;
	}

	/**
	 * Gets the current strip
	 * 
	 * @return current strip
	 */
	public Strip getCurrentStrip() {
		return mCurrent;
	}

	/**
	 * Get the english name for this comic
	 * 
	 * @return name
	 */
	public String getComicName() {
		return mComicName;
	}

	/**
	 * Set the english name for this comic
	 * 
	 * @param name
	 *            name to be set
	 */
	public void setComicName(String name) {
		mComicName = name;
	}

	/**
	 * Reads unread strips count for the current comic
	 * 
	 * @return unread strips count
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws JSONException
	 */
	public int readOnlyUnread() throws FileNotFoundException, IOException, JSONException {
		File json = _getJsonFile();
		if (!json.exists()) {
			return 0;
		}
		JSONObject root = JsonUtils.jsonRoot(new FileInputStream(json));
		if (root.has("mUnread")) {
			return root.getInt("mUnread");
		}
		return 0;
	}

	/**
	 * Helper function to read the properties of this comic from its json file
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws JSONException
	 */
	public void readProperties() throws FileNotFoundException, IOException, JSONException {
		File json = _getJsonFile();
		if (!json.exists()) {
			_readOldFavs();
			_readOldPrevSession();
			return;
		}
		Log.d(TAG, "Reading comic properties from " + json.getPath());
		JSONObject root = JsonUtils.jsonRoot(new FileInputStream(json));
		if (root.has("mLatestUid")) {
			mLatestUid = root.getString("mLatestUid");
		}
		if (root.has("mFirstUid")) {
			mFirstUid = root.getString("mFirstUid");
		}
		if (root.has("mPrevSessionUid")) {
			mPrevSessionUid = root.getString("mPrevSessionUid");
		}
		if (root.has("mDefZoom")) {
			mDefZoom = (float) root.getDouble("mDefZoom");
		}
		if (root.has("mStrips")) {
			JSONArray arr = root.getJSONArray("mStrips");
			int len = arr.length();
			for (int i = 0; i < len; ++i) {
				Strip s = Strip.readFromJsonObject(arr.getJSONObject(i));
				mStrips.put(s.uid(), s);
				if (s.isFavorite()) {
					mFavs.add(s.uid());
				}
			}
		}
		// Find and add all unread comics to the array list.
		if (root.has("mStrips")) {
			JSONArray arr = root.getJSONArray("mStrips");
			int len = arr.length();
			for (int i = 0; i < len; ++i) {
				Strip s = Strip.readFromJsonObject(arr.getJSONObject(i));
				mStrips.put(s.uid(), s);
				if (!s.isRead()) {
					mUnr.add(s.uid());
				}
			}
		}
		Log.d(TAG, "Successfully read comic properties ...");
	}

	/**
	 * For backwards compatibility
	 * 
	 * @throws IOException
	 */
	private void _readOldFavs() throws IOException {
		File old = new File(FileUtils.getComicRoot(), mName);
		if (!old.exists()) {
			return;
		}
		Log.d(TAG, "Getting old favorites from " + old.getPath());
		BufferedReader br = new BufferedReader(new FileReader(old));
		String line = br.readLine();
		mDefZoom = Float.parseFloat(line);
		while ((line = br.readLine()) != null) {
			Strip s = new Strip(line, mCache.cachePath());
			s.setAsFavorite(true);
			Log.d(TAG, line);
			mStrips.put(line, s);
			line = br.readLine(); // no more required
		}
		br.close();
		old.delete(); // this file is no longer required!
	}

	/**
	 * For backwards compatibility
	 * 
	 * @throws IOException
	 */
	private void _readOldPrevSession() throws IOException {
		File old = new File(FileUtils.getComicRoot(), mName + "_lsu");
		if (!old.exists()) {
			return;
		}
		Log.d(TAG, "Getting old previous-session from " + old.getPath());
		BufferedReader br = new BufferedReader(new FileReader(old));
		String line = br.readLine();
		if (line != null) {
			mPrevSessionUid = line;
		}
		br.close();
		old.delete(); // this file is no longer required!
	}

	/**
	 * Helper function to write the properties of this comic into its json file
	 * 
	 * @throws IOException
	 * @throws ComicSDCardFull
	 */
	public void writeProperties() throws IOException, ComicSDCardFull {
		if (mType == TYPE_PREVIEW) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		{
			int unread = 0;
			Iterator<Entry<String, Strip>> itr1 = mStrips.entrySet().iterator();
			while (itr1.hasNext()) {
				Map.Entry<String, Strip> e = itr1.next();
				if (!e.getValue().isRead()) {
					unread++;
				}
			}
			sb.append("\"mUnread\":" + unread + ",\n");
			if (mLatestUid != null) {
				sb.append("\"mLatestUid\":\"" + mLatestUid + "\",\n");
			}
			if (mFirstUid != null) {
				sb.append("\"mFirstUid\":\"" + mFirstUid + "\",\n");
			}
			if (mPrevSessionUid != null) {
				sb.append("\"mPrevSessionUid\":\"" + mPrevSessionUid + "\",\n");
			}
			sb.append("\"mDefZoom\":\"" + mDefZoom + "\",\n");
			sb.append("\"mStrips\": [\n");
			Iterator<Entry<String, Strip>> itr = mStrips.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Strip> e = itr.next();
				e.getValue().toJsonString(sb);
				if (itr.hasNext()) {
					sb.append(",");
				}
				sb.append("\n");
			}
			sb.append("]\n");
		}
		sb.append("}\n");
		FileUtils.storeString(sb.toString(), _getJsonFile());
		Log.d(TAG, "Stored comic properties to the file '" + _getJsonFile().getPath() + "'");
	}

	/**
	 * Returns the default value of zoom
	 * 
	 * @return zoom value
	 */
	public float getDefaultZoom() {
		return mDefZoom;
	}

	/**
	 * Set the default value of zoom
	 * 
	 * @param zoom
	 *            zoom value
	 */
	public void setDefaultZoom(float zoom) {
		Log.d(TAG, "Setting default zoom to " + zoom);
		mDefZoom = zoom;
	}

	/**
	 * Tells whether the comic has any previous sessions or not
	 * 
	 * @return true if it has, else false
	 */
	public boolean hasPreviousSession() {
		return (mPrevSessionUid != null);
	}

	/**
	 * Sets the current strip as favorite or not
	 * 
	 * @param val
	 *            true if it is to be set as favorite
	 */
	public void setCurrentAsFavorite(boolean val) {
		if (mCurrent != null) {
			mCurrent.setAsFavorite(val);
			if (val) {
				mFavs.add(mCurrent.uid());
			} else {
				mFavs.remove(mCurrent.uid());
			}
		}
	}
	
	/**
	 * Whether the current comic is favorite or not
	 * 
	 * @return true if it is
	 */
	public boolean isCurrentFavorite() {
		if (mCurrent == null) {
			return false;
		}
		return mCurrent.isFavorite();
	}


	/**
	 * Whether the current comic has image text or not
	 * 
	 * @return true if it is
	 */
	public boolean currentHasImageText() {
		if (mCurrent == null) {
			return false;
		}
		return mCurrent.hasText();
	}
	
	public boolean containsFavs(){
			return mFavs.size() > 0 ? true : false;
	}
	
	/**
	 * Creates a valid file name out of the strip's title
	 * 
	 * @return valid file name
	 */
	public String currentTitleAsValidFilename() {
		if (mCurrent == null) {
			return null;
		}
		return mCurrent.currentTitleAsValidFilename();
	}

	/**
	 * Helper function to clear the cache
	 */
	public void clearCache() {
		mCache.clearCache();
	}

	/**
	 * Clears all the history associated with this comic
	 */
	public void clearHistory() {
		mStrips.clear();
		mLatestUid = null;
	}

	// //// Strip navigation //////
	/**
	 * Helper function to provide unified interface for navigation among the
	 * strips
	 * 
	 * @param type
	 *            navigation type
	 * @return strip
	 * @throws ComicException
	 */
	public Strip navigateStrip(int type) throws ComicException {
		mCache.makeSpace();
		// If the comic view type is favorite navigate using mFavs array.
		if (mType == TYPE_FAVORITE) {
			return _specialViewNavigation(type, mFavIdx, mFavs);
		}else if (mType == TYPE_UNREAD) {
			return _specialViewNavigation(type, mUnrIdx, mUnr);
		}
		switch (type) {
		case NAV_LATEST:
			return getLatestStrip();
		case NAV_LATEST_FORCE:
			return getLatestStripForcefully();
		case NAV_FIRST:
			return getFirstStrip();
		case NAV_NEXT:
			return getNextStrip();
		case NAV_PREVIOUS:
			return getPreviousStrip();
		case NAV_RANDOM:
			return getRandomStrip();
		case NAV_CURRENT:
			return mCurrent;
		case NAV_PREV_SESSION:
			return getPreviousSessionStrip();
		default:
			ComicException ce = new ComicException("Bad navigation-type passed: " + type);
			throw ce;
		}
	}


	/**
	 * Gets latest strip
	 * 
	 * @return latest strip
	 */
	public Strip getLatestStrip() {
		if ((mLatestUid == null) || !mCacheEnabled) {
			Log.d(TAG, "Latest uid is null (or bg-caching is disabled) , calling getlatestStripUrl...");
			mLatestUid = getLatestStripUrl();
		}
		return _querySetCurrentUid(mLatestUid);
	}

	/**
	 * Gets latest strip forcefully (used during background caching)
	 * 
	 * @return latest strip
	 */
	public Strip getLatestStripForcefully() {
		mLatestUid = getLatestStripUrl();
		return _querySetCurrentUid(mLatestUid);
	}

	/**
	 * Gets the first strip
	 * 
	 * @return first strip
	 */
	public Strip getFirstStrip() {
		mFirstUid = getFirstStripUrl();
		return _querySetCurrentUid(mFirstUid);
	}

	/**
	 * Gets the random strip
	 * 
	 * @return random strip
	 */
	public Strip getRandomStrip() {
		String r = getRandomStripUrl();
		return _querySetCurrentUid(r);
	}

	/**
	 * Gets the next strip
	 * 
	 * @return next strip
	 */
	public Strip getNextStrip() {
		if (isCurrentLatestStrip()) {
			return mCurrent;
		}
		if (!mCurrent.hasNext()) {
			mCurrent.setNext(getNextStripUrl());
		}
		String uid = mCurrent.uid();
		Strip s = _querySetCurrentUid(mCurrent.getNext());
		s.setPrevious(uid);
		return s;
	}

	/**
	 * Gets the previous strip
	 * 
	 * @return previous strip
	 */
	public Strip getPreviousStrip() {
		if (isCurrentFirstStrip()) {
			return mCurrent;
		}
		if (!mCurrent.hasPrevious()) {
			mCurrent.setPrevious(getPreviousStripUrl());
		}
		String uid = mCurrent.uid();
		Strip s = _querySetCurrentUid(mCurrent.getPrevious());
		s.setNext(uid);
		return s;
	}

	/**
	 * Gets the previous session strip
	 * 
	 * @return previous session strip
	 */
	public Strip getPreviousSessionStrip() {
		if (mPrevSessionUid == null) {
			return null;
		}
		return _querySetCurrentUid(mPrevSessionUid);
	}

	/**
	 * Gets the strip from the given url
	 * 
	 * @param url
	 *            url
	 * @return strip
	 * @throws ComicSDCardFull
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 */
	public Strip getStripFromUrl(String url) throws ClientProtocolException, URISyntaxException, IOException, ComicSDCardFull {
		Strip s = _querySetCurrentUid(url);
		s.downloadImage(this);
		return s;
	}

	/**
	 * Download the current strip
	 * 
	 * @throws ClientProtocolException
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws ComicSDCardFull
	 */
	public void downloadCurrentStrip() throws ClientProtocolException, URISyntaxException, IOException, ComicSDCardFull {
		if (mCurrent == null) {
			return;
		}
		mCurrent.downloadImage(this);
	}

	// //// Strip navigation //////

	// //// List of all abstract methods of this class //////
	/**
	 * Get the main page url for the current comic series
	 * 
	 * @return desired url
	 */
	public abstract String getComicWebPageUrl();

	/**
	 * Returns the bound for the current comic
	 * 
	 * @return bound
	 */
	public abstract Bound getBound();

	/**
	 * Type of dialog to be used for choosing comics
	 * 
	 * @return int
	 */
	public abstract int dialogType();

	/**
	 * Gets the url for the latest strip
	 * 
	 * @return url
	 */
	protected abstract String getLatestStripUrl();

	/**
	 * Gets the url for the first strip
	 * 
	 * @return url
	 */
	protected abstract String getFirstStripUrl();

	/**
	 * Gets the url for the next strip
	 * 
	 * @return url
	 */
	protected abstract String getNextStripUrl();

	/**
	 * Gets the url for the previous strip
	 * 
	 * @return url
	 */
	protected abstract String getPreviousStripUrl();

	/**
	 * Gets the url for the previous strip
	 * 
	 * @return url
	 */
	protected abstract String getRandomStripUrl();

	/**
	 * Gives out the list of urls (can be regexes) which are not supposed to be
	 * cached using 'mStrips'
	 * 
	 * @return list
	 */
	protected abstract String[] urlsNotForCaching();

	// //// List of all abstract methods of this class //////

	// //// protected methods //////
	/**
	 * Checks whether the current strip is latest strip or not
	 * 
	 * @return true if it is
	 */
	protected boolean isCurrentLatestStrip() {
		if (mLatestUid == null) {
			mLatestUid = getLatestStripUrl();
		}
		return mLatestUid.equals(mCurrent.uid());
	}

	/**
	 * Checks whether the current strip is first strip or not
	 * 
	 * @return true if it is
	 */
	protected boolean isCurrentFirstStrip() {
		if (mFirstUid == null) {
			mFirstUid = getFirstStripUrl();
		}
		return mFirstUid.equals(mCurrent.uid());
	}

	/**
	 * Query whether the uid already exists in the history
	 * 
	 * @param uid
	 *            uid
	 * @return true if it is, else false
	 */
	protected boolean hasUid(String uid) {
		return mStrips.containsKey(uid);
	}

	/**
	 * Helper function to query for a strip and create if it doesn't exist
	 * 
	 * @param uid
	 *            key to be queried
	 * @return strip
	 */
	protected Strip queryUid(String uid) {
		if (mStrips.containsKey(uid)) {
			return mStrips.get(uid);
		}
		if (uid == null) {

		}
		Strip s = new Strip(uid, mCache.cachePath());
		String[] urls = urlsNotForCaching();
		if (urls != null) {
			for (String url : urls) {
				if (uid.matches(url) || uid.equals(url)) {
					Log.d(TAG, "UID=" + uid + " matches with the regex/url=" + url + ". So not caching this strip");
					File f = new File(s.getImagePath());
					if (f.exists()) {
						f.delete();
					}
					return s;
				}
			}
		}
		if (uid != null) {
			mStrips.put(uid, s);
		}
		return s;
	}

	// //// protected methods //////

	// //// private methods //////
	
	/**
	 * 
	 * Provides a way to navigate menu for Favorites and Unread comics
	 * 
	 * @param type The type of comic being looked for
	 * @param idx index of array of strips
	 * @param mArr the array of strips
	 * @return strip
	 * @throws ComicException
	 */
	private Strip _specialViewNavigation(int type, int[] idx, ArrayList<String> mArr) throws ComicException {
		switch (type) {
		case NAV_LATEST:
			idx[0] = mArr.size() - 1;
			return _getStrip( idx[0],  mArr);
		case NAV_FIRST:
			idx[0] = 0;
			return _getStrip( idx[0],  mArr);
		case NAV_NEXT:
			++idx[0];
			idx[0] = (idx[0] >= mArr.size()) ? 0 : idx[0];
			return _getStrip( idx[0],  mArr);
		case NAV_PREVIOUS:
			--idx[0];
			idx[0] = (idx[0] < 0) ? mArr.size() - 1 : idx[0];
			return _getStrip( idx[0],  mArr);
		case NAV_RANDOM:
			idx[0] = RandUtils.getPositiveInt(mArr.size(), 0);
			return _getStrip( idx[0],  mArr);
		case NAV_CURRENT:
			return mCurrent;
		default:
			ComicException ce = new ComicException("Bad navigation-type passed: " + type);
			throw ce;
		}
	}

	/**
	 * Helper function to query for a strip and set it as current
	 * 
	 * @param uid
	 *            key to be queried
	 * @return strip
	 */
	private Strip _querySetCurrentUid(String uid) {
		mCurrent = queryUid(uid);
		if (mType != TYPE_CACHING) {
			mPrevSessionUid = uid;
		}
		Log.d(TAG, "Current UID = " + uid);
		return mCurrent;
	}

	/**
	 * 
	 * Returns the current strip
	 * 
	 * @param idx index of array to return 
	 * @param mArr array of strip index that are possible to return
	 * @return strip
	 */
	private Strip _getStrip(int idx, ArrayList<String> mArr ) {
		String uid = mArr.get(idx);
		return _querySetCurrentUid(uid);
	}

	/**
	 * Helper function to return the properties json file associated with this
	 * comic
	 * 
	 * @return json file
	 */
	private File _getJsonFile() {
		File f = new File(FileUtils.getComicRoot(), PROPS);
		f.mkdirs();
		return new File(f, mName + ".json");
	}
	// //// private methods //////

}
