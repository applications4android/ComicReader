package com.blogspot.applications4android.comicreader.comictypes;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import com.blogspot.applications4android.comicreader.core.Bound;
import com.blogspot.applications4android.comicreader.core.Downloader;


/**
 * Base class for all archived comics.
 * For a comic to be decided as archived comic, it should have
 * a webpage containing all comic urls which have been released so far
 */
// TODO: add unit-tests
public abstract class YearlyArchivedComic extends ArchivedComic {

	/**
	 * Constructor
	 */
	public YearlyArchivedComic() {
	    super();
	}

	@Override
	protected void fetchAllComicUrls() {
		if(mComicUrls == null) {
			try {
				ArrayList<String> coms = new ArrayList<String>(); 
				int last = Calendar.getInstance().get(Calendar.YEAR);
				for(int year=getFirstYear();year<=last;++year) {
					BufferedReader reader = Downloader.openConnection(new URI(getArchiveUrl(year)));
					ArrayList<String> urls = getAllComicUrls(reader, year);
					reader.close();
					if(neededReversal()) {
						Collections.reverse(urls);
					}
					coms.addAll(urls);
				}
				// NOT WORKING!!
				//mComicUrls = (String[]) coms.toArray();
				int num = coms.size();
				mComicUrls = new String[num];
				for(int i=0;i<num;++i) {
					mComicUrls[i] = coms.get(i);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
				return;
			}
			mLatestId = mComicUrls.length - 1;
			mBound = new Bound(0, (long) (mComicUrls.length - 1));
		}
	}

	// not required, as archive url will be a function of the year!
	@Override
	protected String getArchiveUrl() {
		return null;
	}

	// not required, as this will be a function of the year!
	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		return null;
	}


	////// abstract functions //////
	/**
	 * Main function responsible for parsing the archive url and generating the
	 * list of all comic urls.
	 * @param reader buffered reader from which to parse the comic urls.
	 * @param year current year
	 * @return array of comic url strings
	 * @throws Exception
	 */
	protected abstract ArrayList<String> getAllComicUrls(BufferedReader reader, int year) throws IOException;

	/**
	 * Returns the first year when the comic was released
	 * @return first year
	 */
	protected abstract int getFirstYear();

	/**
	 * Returns the archive url for the given year
	 * @param year year
	 * @return archive url
	 */
	protected abstract String getArchiveUrl(int year);

	/**
	 * Whether a reversal of the list is needed at each year
	 * Reversal is needed if the comic has, in each year's archive page, comics being listed in latest-first-order
	 * @return true if needed
	 */
	protected abstract boolean neededReversal();
	////// abstract functions //////
}
