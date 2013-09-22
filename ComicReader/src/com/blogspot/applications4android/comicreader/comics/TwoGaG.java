package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.blogspot.applications4android.comicreader.comictypes.RandomIndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;

public class TwoGaG extends RandomIndexedComic {

	private Pattern RE_LATEST = Pattern.compile(
			"property=\"og:url\" content=\"http://www.twogag.com/archives/(\\d+)\"");
	private Pattern RE_NEXTURL = Pattern.compile(
			"href=\"http://www\\.twogag\\.com/archives/(\\d+)\" class=\"navi navi-next\" title=\"Next\">");
	private Pattern RE_PREVURL = Pattern.compile(
			"href=\"http://www\\.twogag\\.com/archives/(\\d+)\" class=\"navi navi-prev\" title=\"Previous\">");
	private Pattern RE_COMIC = Pattern.compile(
			"src=\"(http://www\\.twogag\\.com/comics/[^\"]+)\" alt=\"([^\"]+)\"");

	@Override
	protected int getFirstId() {
		return 4;
	}

	@Override
	protected String getRandUrl() {
		// TODO:  TwoGaG doesn't appear to have a "random comic" link
		return "http://www.twogag.com/";
	}

	@Override
	protected int getNextStripId(BufferedReader br, String url) {
		int id = -1;
		try {
			String str;
			while ((str = br.readLine()) != null) {
				Matcher match = RE_NEXTURL.matcher(str);
				if (match.find()) {
					id = Integer.parseInt(match.group(1));
					break;
				}
			}
			setNextId(id);
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
			while ((str = br.readLine()) != null) {
				Matcher match = RE_PREVURL.matcher(str);
				if (match.find()) {
					id = Integer.parseInt(match.group(1));
					break;
				}
			}
			setPreviousId(id);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	protected String getFrontPageUrl() {
		return "http://www.twogag.com/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.twogag.com";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader)
			throws ComicLatestException, IOException {
		String str;
		int latest_id = -1;
		while ((str = reader.readLine()) != null) {
			Matcher match = RE_LATEST.matcher(str);
			if (match.find()) {
				latest_id = Integer.parseInt(match.group(1));
				break;
			}
		}
		if (latest_id == -1) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			throw new ComicLatestException(msg);
		}
		return latest_id;
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.twogag.com/archives/" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll(".*/archives/", ""));
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String str;
		String final_str = null;
		String final_title = null;
		String final_itext = null;
		while ((str = reader.readLine()) != null) {
			Matcher match = RE_COMIC.matcher(str);
			if (match.find()) {
				final_str = match.group(1);
				final_title = match.group(2);
				continue;
			}
			match = RE_PREVURL.matcher(str);
			if (match.find()) {
				setPreviousId(Integer.parseInt(match.group(1)));
				continue;
			}
			match = RE_NEXTURL.matcher(str);
			if (match.find()) {
				setNextId(Integer.parseInt(match.group(1)));
				continue;
			}
			final String caption_search = "property=\"og:description\" content=\"";
			int index1 = str.indexOf(caption_search);
			if (index1 != -1) {
				final_itext = str.substring(index1 + caption_search.length());
				final_itext = final_itext.replaceAll("\" />.*", "");
			}
		}
		strip.setTitle("Two Guys and Guy: " + final_title);
		strip.setText(final_itext);
		return final_str;
	}

	@Override
	protected int parseForPrevId(String line, int def) {
		if (line == null) {
			return def;
		}
		line = line.replaceAll(".*/archives/", "");
		return Integer.parseInt(line);
	}

	@Override
	protected int parseForNextId(String line, int def) {
		return parseForPrevId(line, def);
	}
}
