package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import android.util.Log;

import com.blogspot.applications4android.comicreader.comictypes.RandomIndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;

public class Cyanide extends RandomIndexedComic {

	private static final String Cyanide = "Cyanide";
	
	@Override
	protected int getFirstId() {
		return 15;
	}

	@Override
	protected String getRandUrl() {
		return "http://www.explosm.net/comics/random/";
	}

	@Override
	protected int getNextStripId(BufferedReader br, String url) {
		int id = -1;
		try {
			String str;
			String final_next = null;
			while ((str = br.readLine()) != null) {
				if(str.indexOf("/\" class=\"next-comic\"") != -1) {
					final_next = str;
				}
			}
			Log.d(Cyanide, "In getNextStripId, final_next = "+final_next);
			setNextId(parseForNextId(final_next, getLatestId()));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	protected int getPreviousStripId(BufferedReader br, String url) {
		int id = -1;
		try {
			String str;
			String final_prev = null;
			while ((str = br.readLine()) != null) {
				if(str.indexOf("/\" class=\"previous-comic\"") != -1) {
					final_prev = str;
				}
			}
			Log.d(Cyanide, "In getPreviousStripId, final_prev = "+final_prev);
			setPreviousId(parseForPrevId(final_prev, getFirstId()));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	@Override
	protected int parseForLatestId(BufferedReader reader) throws ComicLatestException, IOException {
		String str;
		String final_str = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("explosm.net%2Fcomics");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		final_str = final_str.replaceAll(".*comics%2F","");
		final_str = final_str.replaceAll("%2F.*","");
		Log.d(Cyanide, "In parseForLatestId, final_str = "+final_str);
		return Integer.parseInt(final_str);
	}

	@Override
	public String getStripUrlFromId(int num) {
		Log.d(Cyanide, "In getStripUrlFromId, num = "+num);
		return "http://www.explosm.net/comics/" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		String str = url.replaceAll("http://www.explosm.net/comics/", "");
		Log.d(Cyanide, "In getIdFromStripUrl, str = "+str);
		return Integer.parseInt(str);
	}

	@Override
	protected String getFrontPageUrl() {
		return "http://explosm.net";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://explosm.net";
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
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
			if(str.indexOf("\"og:image\"") != -1) {
				final_str = str;
			}
			if(str.indexOf("\"og:url\"") != -1) {
				final_title = str;
			}
			if(str.indexOf("/\" class=\"next-comic\"") != -1) {
				final_next = str;
			}
			if(str.indexOf("/\" class=\"previous-comic\"") != -1) {
				final_prev = str;
			}
		}
		Log.d(Cyanide, "In parse, final_str = "+final_str);		
		Log.d(Cyanide, "In parse, final_title = "+final_title);		
		Log.d(Cyanide, "In parse, final_next = "+final_next);		
		Log.d(Cyanide, "In parse, final_prev = "+final_prev);		
		final_str = final_str.replaceAll(".*og:image\" content=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_title = final_title.replaceAll(".*net/comics/","");
		final_title = final_title.replaceAll("/\".*","");
		strip.setTitle(final_title);
		strip.setText("-NA-");
		// set the next and previous comic IDs from the current url's html data
		setPreviousId(parseForPrevId(final_prev, -1));
		setNextId(parseForNextId(final_next, -1));
		return final_str;
	}

	@Override
	protected int parseForPrevId(String line, int def) {
		if(line == null) {
			return def;
		}
		line = line.replaceAll(".*comics/", "");
		line = line.replaceAll("/\".*", "");
		return Integer.parseInt(line);
	}

	@Override
	protected int parseForNextId(String line, int def) {
		return parseForPrevId(line, def);
	}

}
