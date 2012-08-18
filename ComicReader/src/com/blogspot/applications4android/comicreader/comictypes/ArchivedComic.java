package com.blogspot.applications4android.comicreader.comictypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import com.blogspot.applications4android.comicreader.core.Bound;
import com.blogspot.applications4android.comicreader.core.Downloader;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


/**
 * Base class for all archived comics.
 * For a comic to be decided as archived comic, it should have
 * a webpage containing all comic urls which have been released so far
 */
// TODO: add unit-tests
public abstract class ArchivedComic extends IndexedComic {
	/** list of all comics */
	protected String[] mComicUrls;


	/**
	 * Constructor
	 */
	public ArchivedComic() {
		super();
		mComicUrls = null;
	}

	@Override
	protected String getLatestStripUrl() {
		fetchAllComicUrls();
		return getStripUrlFromId(mLatestId);
	}

	/**
	 * Fetch all the comic urls
	 */
	protected void fetchAllComicUrls() {
		if(mComicUrls == null) {
			try {
				BufferedReader reader = Downloader.openConnection(new URI(getArchiveUrl()));
				mComicUrls = getAllComicUrls(reader);
				reader.close();
			}
			catch(Exception e) {
				e.printStackTrace();
				return;
			}
			mLatestId = mComicUrls.length - 1;
			mBound = new Bound(0, (long) (mComicUrls.length - 1));
		}
	}

	@Override
	protected Strip queryUid(String uid) {
		if(!hasUid(uid)) {
			fetchAllComicUrls();
		}
		return super.queryUid(uid);
	}

	@Override
	protected int getFirstId() {
		return 0;
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws ComicLatestException, IOException {
		return 0;
	}

	@Override
	protected String getFrontPageUrl() {
		return null;
	}

	@Override
	public String getStripUrlFromId(int num) {
		fetchAllComicUrls();
		if(num >= mComicUrls.length) {
			return mComicUrls[mComicUrls.length];
		}
		if(num < 0) {
			return mComicUrls[0];
		}
		return mComicUrls[num];
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		fetchAllComicUrls();
		int i = 0;
		for(String u : mComicUrls) {
			if(u.equals(url)) {
				return i;
			}
			++i;
		}
		return -1;
	}


	////// abstract functions //////
	/**
	 * Main function responsible for parsing the archive url and generating the
	 * list of all comic urls.
	 * @param reader buffered reader from which to parse the comic urls.
	 * @return array of comic url strings
	 * @throws Exception
	 */
	protected abstract String[] getAllComicUrls(BufferedReader reader) throws IOException;

	/**
	 * Return the archive url
	 * @return url
	 */
	protected abstract String getArchiveUrl();
	////// abstract functions //////

}
