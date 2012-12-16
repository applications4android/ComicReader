package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.RandomIndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;

public class Cyanide extends RandomIndexedComic {

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
				if(str.indexOf("case 39") != -1) {
					final_next = br.readLine();
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
				if(str.indexOf("case 37") != -1) {
					final_prev = br.readLine();
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
	public String getStripUrlFromId(int num) {
		return "http://www.explosm.net/comics/" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		String str = url.replaceAll("http://www.explosm.net/comics/", "");
		return Integer.parseInt(str);
	}

	@Override
	protected String getFrontPageUrl() {
		return "http://www.explosm.net/comics/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.explosm.net/";
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
		final_str = final_str.replaceAll(".*Cyanide and Happiness, a daily webcomic\" src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_title = final_title.replaceAll(".*<title>","");
		final_title = final_title.replaceAll(" - Explosm.*","");
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
