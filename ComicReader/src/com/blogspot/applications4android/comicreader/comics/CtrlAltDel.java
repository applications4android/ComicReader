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



public class CtrlAltDel extends ArchivedComic {
	private static final int mFirstYr = 2002;
	private int mCurrYr;
	private static final String mArchiveStr = "http://www.cad-comic.com/cad/archive/";


	@Override
	public String getComicWebPageUrl() {
		return "http://www.cad-comic.com/cad/";
	}

	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str, str_temp;
		String search = "/cad/" + mCurrYr;
		int i;
		while((str = reader.readLine()) != null) {
			i = str.indexOf(search);
			if (i != -1) {
				str_temp = str;
				str_temp=str_temp.replaceAll(".*?href=\"","");;
				str_temp=str_temp.replaceAll("\".*$", "");
				str_temp="http://www.cad-comic.com"+str_temp;
				m_com.add(str_temp);
			}
		}
		String[] m_com_urls = new String[m_com.size()];
		m_com.toArray(m_com_urls);
		return m_com_urls;
	}

	@Override
	protected void fetchAllComicUrls() {
		if(mComicUrls == null) {
			try {
				ArrayList<String> all_yrs = new ArrayList<String>();
				// get for all years
				Calendar cal = Calendar.getInstance();
				int f = cal.get(Calendar.YEAR);
				for(mCurrYr=mFirstYr;mCurrYr<=f;mCurrYr++) {
					URI u = null;
					try {
						u = new URI(mArchiveStr+mCurrYr);
					}
					catch(Exception e) {} // This should never occur!!
					BufferedReader reader = Downloader.openConnection(u);
					String[] urls = getAllComicUrls(reader);
					for(int i=urls.length-1;i>=0;i--) {
						all_yrs.add(urls[i]);
					}
					reader.close();
				}
				mComicUrls = new String[all_yrs.size()];
				all_yrs.toArray(mComicUrls);
			}
			catch(Exception e) {
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
		return "http://www.cad-comic.com/cad/archive/";
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
    protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		boolean comic_found = true;
		String str;
		String final_str = null;
		String final_title = null;
		while ((str = reader.readLine()) != null) {
			if(str.indexOf("Comic could not be found.") != -1) {
				comic_found = false;
			}
			if(!comic_found) {
				continue;
			}
			int index1 = str.indexOf("/comics/cad-");
			if (index1 != -1) {
				final_str = str;
				final_title = str;
			}
		}
		/*
		if(!comic_found) {
			String msg = "Failed to find the comic for URL="+strip.uid();
			ComicNotFoundException cnf = new ComicNotFoundException(msg);
			throw cnf;
		}
		if((final_str == null) || (final_title == null)) {
			String msg = "Failed to find the stripURL for URL="+strip.m_comic_url.toExternalForm();
			msg += " final_str="+final_str+" final_title="+final_title;
			ComicParseException cpe = new ComicParseException(msg);
			throw cpe;
		}
		*/
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_title = final_title.replaceAll(".*title=\"","");
		final_title = final_title.replaceAll("\".*","");
		strip.setTitle("Ctrl Alt Del - "+final_title);
		strip.setText("-NA-");
		return final_str;
	}

}
