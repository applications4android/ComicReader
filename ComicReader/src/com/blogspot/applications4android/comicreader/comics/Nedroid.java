package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Bound;
import com.blogspot.applications4android.comicreader.core.Downloader;
import com.blogspot.applications4android.comicreader.core.Strip;

public class Nedroid extends ArchivedComic {
	private static final int mFirstYr = 2005;
	private int mCurrYr;
	private static final String mArchiveStr = "http://www.cad-comic.com/cad/archive/";

	@Override
	public String getComicWebPageUrl() {
		return "http://nedroid.com";
	}

	@Override
	protected String[] getAllComicUrls(BufferedReader reader)
			throws IOException {
		ArrayList<String> m_com = new ArrayList<String>();
		String str, str_temp;
		String search = "\t<a href=\"http://nedroid.com/" + mCurrYr + "/";
		int i;
		while ((str = reader.readLine()) != null) {
			i = str.indexOf(search);
			if (i != -1) {
				// remove the html surrounding the URL
				if (!str.contains("td")) {
					str_temp = str;
					str_temp = str_temp.replaceAll(".*?href=\"", "");
					str_temp = str_temp.replaceAll("\".*$", "");
					m_com.add(str_temp);
				}
			}
		}
		String[] m_com_urls = new String[m_com.size()];
		m_com.toArray(m_com_urls);
		return m_com_urls;
	}

	@Override
	protected void fetchAllComicUrls() {
		if (mComicUrls == null) {
			try {
				ArrayList<String> all_yrs = new ArrayList<String>();
				// get for all years
				Calendar cal = Calendar.getInstance();
				int f = cal.get(Calendar.YEAR);

				for (mCurrYr = mFirstYr; mCurrYr <= f; mCurrYr++) {
					URI u = null;
					try {
						u = new URI("http://nedroid.com/" + mCurrYr + "/");
					} catch (Exception e) {
					} // This should never occur!!
					BufferedReader reader = Downloader.openConnection(u);
					String[] urls = getAllComicUrls(reader);
					for (int i = 0; i <= urls.length - 1; i++) {
						all_yrs.add(urls[i]);
					}
					reader.close();
				}
				mComicUrls = new String[all_yrs.size()];
				all_yrs.toArray(mComicUrls);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		mBound = new Bound(0, (long) (mComicUrls.length - 1));
	}

	@Override
	protected String getLatestStripUrl() {
		fetchAllComicUrls();
		return getStripUrlFromId(mComicUrls.length - 1);
	}

	@Override
	protected String getArchiveUrl() {
		return "http://nedroid.com/";
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String str;
		String final_title = null;
		String final_url = null;
		String final_itext = null;
		
		while ((str = reader.readLine()) != null) {
			// Get URL
			int index1 = str.indexOf("http://nedroid.com/comics");
			if (index1 != -1) {
				final_url = str;
				final_title = str;
				final_itext = str;
			}
			// Get Title

		}
		final_url = final_url.replaceAll(".*src=\"", "");
		final_url = final_url.replaceAll("\".*", "");
		final_title = final_title.replaceAll(".*alt=\"", "");
		final_title = final_title.replaceAll("\".*", "");
		final_itext = final_itext.replaceAll(".*title=\"", "");
		final_itext = final_itext.replaceAll("\".*", "");
		strip.setTitle("Nedroid: " + final_title);
		strip.setText(final_itext);
		return final_url;
	}

}
