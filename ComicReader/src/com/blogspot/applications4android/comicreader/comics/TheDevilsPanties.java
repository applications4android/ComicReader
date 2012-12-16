package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.RandomIndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;

public class TheDevilsPanties extends RandomIndexedComic {

	@Override
	protected String getRandUrl() {
		return getStripUrlFromId(getFirstId());
	}

	@Override
	protected int getNextStripId(BufferedReader br, String url) {
		int id = -1;
		try {
			String str;
			String final_next = null;
			while ((str = br.readLine()) != null) {
				if(str.indexOf("navi navi-next") != -1) {
					final_next = str;
				}
			}
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
				if(str.indexOf("navi navi-prev") != -1) {
					final_prev = str;
				}
			}
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
			int index1 = str.indexOf("comic-id-");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
    	final_str = final_str.replaceAll(".*comic-id-","");
    	final_str = final_str.replaceAll("\".*","");
	    return Integer.parseInt(final_str);
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://thedevilspanties.com/archives/" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*archives/", ""));
	}

	@Override
	protected String getFrontPageUrl() {
		return "http://thedevilspanties.com/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://thedevilspanties.com/";
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
    	String str;
		String final_str = null;
		String final_title = null;
		String final_next = null;
		String final_prev = null;
		while((str = reader.readLine()) != null) {
			if(str.indexOf("comicpane") != -1) {
				final_str = str;
				final_title = str;
			}
			else if(str.indexOf("navi navi-prev") != -1) {
				final_prev = str;
			}
			else if(str.indexOf("navi navi-next") != -1) {
				final_next = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
    	final_title = final_title.replaceAll(".*alt=\"","");
    	final_title = final_title.replaceAll("\".*","");
		strip.setTitle("The Devils Panties: "+final_title);
		strip.setText("-NA-");
		// set the next and previous comic IDs from the current url's html data
		setNextId(parseForNextId(final_next, -1));
		setPreviousId(parseForPrevId(final_prev, -1));
		return final_str; 
	}

	@Override
	protected int getFirstId() {
		return 300;
	}

	@Override
	protected int parseForPrevId(String line, int def) {
		if(line == null) {
			return def;
		}
		line = line.replaceAll(".*href=\"", "");
		line = line.replaceAll("\".*", "");
		line = line.replaceAll("/archives/", "");
		try {
			return Integer.parseInt(line);
		}
		catch(Exception e) {
			return def;
		}
	}

	@Override
	protected int parseForNextId(String line, int def) {
		return parseForPrevId(line, def);
	}
}
