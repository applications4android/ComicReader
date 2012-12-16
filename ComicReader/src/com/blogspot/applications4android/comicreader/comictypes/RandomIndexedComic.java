package com.blogspot.applications4android.comicreader.comictypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

import com.blogspot.applications4android.comicreader.core.Downloader;

/**
 * Base class for all those comics which are indexed, but not in order
 */
public abstract class RandomIndexedComic extends IndexedComic {
	/** next strip's id */
	protected int mNextId = -1;
	/** previous strip's id */
	protected int mPrevId = -1;

	/** list of urls to be not cached */
	private String[] DONT_CACHE = null;


	/**
	 * Function to return the random url
	 * @return random url
	 */
	protected abstract String getRandUrl();

	/**
	 * Evaluate the next strip's index from the current strip's url
	 * @param br reader for the current strip's url connection
	 * @param url current strip's url
	 * @return next strip's index
	 */
	protected abstract int getNextStripId(BufferedReader br, String url);

	/**
	 * Evaluate the previous strip's index from the current strip's url
	 * @param br reader for the current strip's url connection
	 * @param url current strip's url
	 * @return previous strip's index
	 */
	protected abstract int getPreviousStripId(BufferedReader br, String url);

	/**
	 * Given a line containing the previous strip's url, return the id
	 * @param line line of interest
	 * @param def default value to be returned
	 * @return prev Id
	 */
	protected abstract int parseForPrevId(String line, int def);

	/**
	 * Given a line containing the next strip's url, return the id
	 * @param line line of interest
	 * @param def default value to be returned
	 * @return next Id
	 */
	protected abstract int parseForNextId(String line, int def);

	/**
	 * Helper function to set the next id
	 * @param id next id
	 */
	protected void setNextId(int id) {
		mNextId = id;
	}

	/**
	 * Helper function to set the previous id
	 * @param id prev id
	 */
	protected void setPreviousId(int id) {
		mPrevId = id;
	}

	@Override
	protected String getNextStripUrl() {
		int id = mNextId;
		if(id < getFirstId()) {
			String url = getCurrentStrip().uid();
			try {
				BufferedReader reader = Downloader.openConnection(new URI(url));
				id = getNextStripId(reader, url);
				reader.close();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return getStripUrlFromId(id);
	}

	@Override
	protected String getPreviousStripUrl() {
		int id = mPrevId;
		if(id < getFirstId()) {
			String url = getCurrentStrip().uid();
			try {
				BufferedReader reader = Downloader.openConnection(new URI(url));
				id = getPreviousStripId(reader, url);
				reader.close();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return getStripUrlFromId(id);
	}

	@Override
	protected String[] urlsNotForCaching() {
		if(DONT_CACHE != null) {
			return DONT_CACHE;
		}
		DONT_CACHE = new String[] { getRandUrl() };
		return DONT_CACHE;
	}

	@Override
	protected String getRandomStripUrl() {
		return getRandUrl();
	}
}
