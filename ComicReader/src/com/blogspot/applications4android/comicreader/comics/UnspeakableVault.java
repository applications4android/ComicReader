package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;

public class UnspeakableVault extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://www.goominet.com/unspeakable-vault/";
	}

	public String getComicWebPageUrl() {
		return "http://www.goominet.com/unspeakable-vault/";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException,
			ComicLatestException {
		String str;
		String final_str = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("og:title");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if (final_str == null) {
			String msg = "Failed to get the latest id for "
					+ this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		final_str = final_str.replaceAll(".*strip\\s", "");
		final_str = final_str.replaceAll(":.*", "");
		return Integer.parseInt(final_str);
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.goominet.com/unspeakable-vault/vault/" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		String temp = url.replaceAll("http.*vault/", "");
		temp = temp.replaceAll("/", "");
		return Integer.parseInt(temp);
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
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("og:image");
			if (index1 != -1) {
				final_str = str;
			}
			int index3 = str.indexOf("og:title");
			if (index3 != -1) {
				final_title = str;
			}
		}
		final_str = final_str.replaceAll(".*=\"", "");
		final_str = final_str.replaceAll("\".*", "");
		final_title = final_title.replaceAll(".*=\"", "");
		final_title = final_title.replaceAll("\".*", "");
		strip.setTitle(final_title);
		strip.setText(null);
		return final_str;
	}
}
