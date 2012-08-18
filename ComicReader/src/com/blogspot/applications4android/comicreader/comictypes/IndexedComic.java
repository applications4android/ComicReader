package com.blogspot.applications4android.comicreader.comictypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.util.Random;

import com.blogspot.applications4android.comicreader.core.Bound;
import com.blogspot.applications4android.comicreader.core.Comic;
import com.blogspot.applications4android.comicreader.core.Downloader;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


/**
 * Base class for all indexed comics
 */
// TODO: add unit-tests
public abstract class IndexedComic extends Comic {
	/** bound for this comic */
	protected Bound mBound;
	/** latest comic id */
	protected int mLatestId;


	/**
	 * Constructor
	 */
	public IndexedComic() {
		super();
		mLatestId = -1;
		mBound = null;
	}

	/**
	 * Helper function to get the current comic id
	 * @return id
	 */
	public int getCurrentId() {
		Strip s = getCurrentStrip();
		return getIdFromStripUrl(s.uid());
	}

	/**
	 * For those days which do not have a comic strip
	 * If the given indexed comic series WAS irregular previously but now has been
	 * regular, then you have to override this function!
	 * @param in the index which needs to take care of this
	 * @param increment which date needs to be shown instead.
	 * @return updated index
	 */
	public int addException(int in, int increment) {
		return in;
	}

	@Override
	public Bound getBound() {
		if(mBound == null) {
			mBound = new Bound((long) getFirstId(), (long) getLatestId());
		}
		return mBound;
	}

	@Override
	public int dialogType() {
		return Comic.DIALOG_NUMBER;
	}

	@Override
	protected String getLatestStripUrl() {
		if(mLatestId < 0) {
			try {
				BufferedReader br = Downloader.openConnection(new URI(getFrontPageUrl()));
				mLatestId = parseForLatestId(br);
				br.close();
			}
			catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return getStripUrlFromId(mLatestId);
	}

	@Override
	protected String getFirstStripUrl() {
		return getStripUrlFromId(getFirstId());
	}

	@Override
	protected String getNextStripUrl() {
		int id = getCurrentId();
		id = addException(id+1, 1);
		return getStripUrlFromId(id);
	}

	@Override
	protected String getPreviousStripUrl() {
		int id = getCurrentId();
		id = addException(id-1, -1);
		return getStripUrlFromId(id);
	}

	@Override
	protected String getRandomStripUrl() {
		Random rnd = new Random();
		int num_temp = rnd.nextInt();
		// always the numbers must be positive!
		if(num_temp < 0) {
			num_temp = -num_temp;
		}
		int fid = getFirstId();
		int lid = getLatestId();
		int del = lid - fid + 1;
		int num = (num_temp % del) + fid;
		num = addException(num, 1);
		if(num > lid) {
			num = lid;
		}
		return getStripUrlFromId(num);
	}

	/**
	 * Returns the latest comic-id
	 * @return latest comic-id
	 */
	protected int getLatestId() {
		if(mLatestId < 0) {
			mLatestId = getIdFromStripUrl(mLatestUid);
		}
		return mLatestId;
	}

	/**
	 * Returns the first comic-id
	 * @return first comic-id
	 */
	protected int getFirstId() {
		return 1;
	}


	////// abstract functions //////
	/**
	 * Helper function to parse the latest comic's html page in order to get the latest comic id.
	 * @param reader reader from which to parse for latest id
	 * @return latest comic id
	 * @throws ComicLatestException
	 * @throws IOException
	 */
	protected abstract int parseForLatestId(BufferedReader reader) throws ComicLatestException, IOException;

	/**
	 * Get the strip URL from the input comic-id.
	 * @param num the comic-id
	 * @return the strip URL
	 */
	public abstract String getStripUrlFromId(int num);

	/**
	 * Get the comic-id from strip URL
	 * @param url comic url
	 * @return the comic-id
	 */
	protected abstract int getIdFromStripUrl(String url);

	/**
	 * Return the front page URL from where to get the latest comic URL.
	 * @return front page URL
	 */
	protected abstract String getFrontPageUrl();
	////// abstract functions //////


	@Override
	protected String[] urlsNotForCaching() {
		return null;
	}

}
