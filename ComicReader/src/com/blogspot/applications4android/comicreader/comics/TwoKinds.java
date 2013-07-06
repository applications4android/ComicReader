package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Downloader;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;

/**
 * 
 * @author C. Albert
 * 
 */
public class TwoKinds extends IndexedComic {
	private String prevComic = "http://twokinds.keenspot.com/archive.php";
	
	@Override
	protected String getFrontPageUrl() {
		return "http://twokinds.keenspot.com/";
	}

	public String getComicWebPageUrl() {
		return "http://twokinds.keenspot.com/";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException,
			ComicLatestException {
		String str;
		String final_str = null;

		URI u = null;
		try {
			u = new URI(prevComic);
		} catch (Exception e) {
		} // This should never occur!!

		try {
			BufferedReader reader2 = Downloader.openConnection(u);
			while ((str = reader2.readLine()) != null) {
				int index1 = str.indexOf("cg_nav1");
				if (index1 != -1) {
					final_str = str;
				}
			}
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (final_str == null) {
			String msg = "Failed to get the latest id for "
					+ this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}

		final_str = final_str.replaceAll("rel=\"p.*", "");
		final_str = final_str.replaceAll(".*p=", "");
		final_str = final_str.replaceAll("\"\\sid.*", "");
		return Integer.parseInt(final_str) + 2;
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://twokinds.keenspot.com/archive.php?p=" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*com/archive\\.php\\?p=",""));
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		
		
		//Trying to fix the problem with getting the latest comic by checking first
		//if the current comic url is the latest
		String temp = url.replaceAll(".*=", "");		
		if (mLatestId == Integer.parseInt(temp)){
			URI u = null;
			try {
				u = new URI(getFrontPageUrl());
			} catch (Exception e) {
			} // This should never occur!!
			try {
				reader = Downloader.openConnection(u);
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		

		
		
		String str;
		String final_str = null;
		String final_title = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("><img src=\"http://t");
			if (index1 != -1) {
				final_str = str;
				final_title = str;
			}
			int index3 = str.indexOf("x\" src=\"http://c");
			if (index3 != -1) {
				final_str = str;
				final_title = "";
			}
		}
		final_str = final_str.replaceAll(".*src=\"", "");
		final_str = final_str.replaceAll("\".*", "");
		final_title = final_title.replaceAll(".*tle=\"", "");
		final_title = final_title.replaceAll("\".*", "");
		strip.setTitle("Two Kinds: " + final_title);
		return final_str;
	}
}
