package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import android.util.Log;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Downloader;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class Cyanide extends IndexedComic {
	private int mNextId = -1;
	private int mPrevId = -1;
	private static final String RAND_URL = "http://www.explosm.net/comics/random/";
	private static final String[] DONT_CACHE = new String[]{RAND_URL};


	@Override
	protected String getFrontPageUrl() {
		return "http://www.explosm.net/comics/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.explosm.net/";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("<title>Cyanide & Happiness");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		final_str = final_str.replaceAll(".*Happiness #","");
		final_str = final_str.replaceAll(" - Explosm.*","");
		return Integer.parseInt(final_str);
	}

	@Override
	protected String getNextStripUrl() {
		int id = mNextId;
		if(id < getFirstId()) {
			try {
				id = _getNextStripId(new URI(getCurrentStrip().uid()));
			}
			catch (URISyntaxException e) { // this should never occur!
				e.printStackTrace();
			}
		}
		return getStripUrlFromId(id);
	}

	@Override
	protected String getPreviousStripUrl() {
		int id = mPrevId;
		if(id < getFirstId()) {
			try {
				id = _getPrevStripId(new URI(getCurrentStrip().uid()));
			}
			catch (URISyntaxException e) { // this should never occur!
				e.printStackTrace();
			}
		}
		return getStripUrlFromId(id);
	}

	@Override
	protected int getFirstId() {
		return 15;
	}

	@Override
	protected String getRandomStripUrl() {
		return RAND_URL;
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.explosm.net/comics/" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		String str = url.replaceAll("http://www.explosm.net/comics/", "");
		return Integer.parseInt(str);
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
		String final_next = null;
		String final_prev = null;
		while ((str = reader.readLine()) != null) {
			if(str.indexOf("Comic could not be found.") != -1) {
				comic_found = false;
			}
			if(!comic_found) {
				continue;
			}
			if(str.indexOf("Cyanide and Happiness, a daily webcomic") != -1) {
				final_str = str;
			}
			if(str.indexOf("<title>Cyanide & Happiness ") != -1) {
				final_title = str;
			}
			if(str.indexOf("case 39") != -1) {
				final_next = reader.readLine();
			}
			if(str.indexOf("case 37") != -1) {
				final_prev = reader.readLine();
			}
		}
		Log.d("CYanide", "comic_found="+comic_found);
		/*
		if(!comic_found) {
			String msg = "Failed to find the comic for URL="+strip.m_comic_url.toExternalForm();
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
		final_str = final_str.replaceAll(".*Cyanide and Happiness, a daily webcomic\" src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_title = final_title.replaceAll(".*<title>","");
		final_title = final_title.replaceAll(" - Explosm.*","");
		strip.setTitle(final_title);
		strip.setText("-NA-");
		if(final_next != null) {
			final_next = final_next.replaceAll(".*comics/", "");
			final_next = final_next.replaceAll("/\".*", "");
			mNextId = Integer.parseInt(final_next);
		}
		else {
			mNextId = -1;
		}
		if(final_prev != null) {
			final_prev = final_prev.replaceAll(".*comics/", "");
			final_prev = final_prev.replaceAll("/\".*", "");
			mPrevId = Integer.parseInt(final_prev);
		}
		else {
			mPrevId = -1;
		}
		return final_str;
	}

	@Override
	protected String[] urlsNotForCaching() {
		return DONT_CACHE;
	}

	/**
	 * Get the next strip id
     * @param url url from which to parse
     * @return desired id
	 */
	private int _getNextStripId(URI url) {
		int id = -1;
		try {
			BufferedReader reader = Downloader.openConnection(url);
			String str;
			String final_next = null;
			while ((str = reader.readLine()) != null) {
				if(str.indexOf("case 39") != -1) {
					final_next = reader.readLine();
				}
			}
			reader.close();
			if(final_next != null) {
				final_next = final_next.replaceAll(".*comics/", "");
				final_next = final_next.replaceAll("/\".*", "");
				id = Integer.parseInt(final_next);
			}
			else {
				id = mLatestId;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * Get the previous strip id
     * @param url url from which to parse
     * @return desired id
	 */
	private int _getPrevStripId(URI url) {
		int id = -1;
		try {
			BufferedReader reader = Downloader.openConnection(url);
			String str;
			String final_prev = null;
			while ((str = reader.readLine()) != null) {
				if(str.indexOf("case 37") != -1) {
					final_prev = reader.readLine();
				}
			}
			reader.close();
			if(final_prev != null) {
				final_prev = final_prev.replaceAll(".*comics/", "");
				final_prev = final_prev.replaceAll("/\".*", "");
				id = Integer.parseInt(final_prev);
			}
			else {
				id = getFirstId();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return id;
	}
}
