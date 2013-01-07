package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Bound;
import com.blogspot.applications4android.comicreader.core.Downloader;
import com.blogspot.applications4android.comicreader.core.Strip;

//This comic does not have numbers to work with each URL is the title of the comic.
//So we will use the archive to get the comics is order.
public class MenageA3 extends ArchivedComic {
	@Override
	public String getComicWebPageUrl() {
		return "http://www.ma3comic.com/strips-ma3/";
	}

	@Override
	protected String[] getAllComicUrls(BufferedReader reader)
			throws IOException {
		ArrayList<String> m_com = new ArrayList<String>();
		String str, str_temp;
		String search = "http://www.ma3comic.com/strips-ma3";
		int i;
		while ((str = reader.readLine()) != null) {
			i = str.indexOf(search);
			if (i != -1) {
				//remove the html surrounding the URL
				if (!str.contains("ARCHIVE")) {
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
				ArrayList<String> all_vols = new ArrayList<String>();
				// Archive has volumes that update no according to calendar
				// So find number of archives first.
				ArrayList<String> vol_urls = getNumOfVols();

				for (String currentVol : vol_urls) {
					URI u = null;
					try {
						u = new URI(currentVol);
					} catch (Exception e) {
					} // This should never occur!!
					BufferedReader reader = Downloader.openConnection(u);
					String[] urls = getAllComicUrls(reader);
					for (int i = 0; i <= urls.length - 1; i++) {
						all_vols.add(urls[i]);
					}
					reader.close();
				}
				mComicUrls = new String[all_vols.size()];
				all_vols.toArray(mComicUrls);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
		mBound = new Bound(0, (long) (mComicUrls.length - 1));
	}

	//Finds the urls for volumes. This number can be updated.
	private ArrayList<String> getVolURL() {
		String str;
		String search = "http://www.menagea3.net/archive/volume";
		String mArchiveStr = "http://www.ma3comic.com/archive";
		//We use an arraylist because the website does not do a great job on its design.
		//They have duplicate urls and I use contains to check that there are no duplicates
		ArrayList<String> vol_urls = new ArrayList<String>();
		Pattern p = Pattern.compile(search + "\\d");

		try {
			URI u = null;
			try {
				u = new URI(mArchiveStr);
			} catch (Exception e) {
			} // This should never occur!!

			BufferedReader reader = Downloader.openConnection(u);
			int i;
			while ((str = reader.readLine()) != null) {
				i = str.indexOf(search);
				if (i != -1) {
					//Find the patter of the volume and add to the Arraylist
					Matcher m = p.matcher(str);
					while (m.find()) {
						if (!vol_urls.contains(m.group())) {
							vol_urls.add(m.group());
						}
					}
				}
			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
			return vol_urls;
		}

		return vol_urls;
	}

	private int countOccurrences(String str, String pattern) {

		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		int count = 0;
		while (m.find()) {
			count += 1;
		}
		return count;
	}

	@Override
	protected String getLatestStripUrl() {
		fetchAllComicUrls();
		return getStripUrlFromId(mComicUrls.length - 1);
	}

	@Override
	protected String getArchiveUrl() {
		return "http://www.ma3comic.com/archive/volume";
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
			// This gets the location of the image
			int index1 = str.indexOf("comics/mat");
			if (index1 != -1) {
				final_str = str;
			}
			// This gets the title of the commic
			int index2 = str.indexOf("<title>M&eacute;nage");
			if (index2 != -1) {
				final_title = str;
			}
		}
		// Pattern matching to remove unwanted characters.
		final_str = final_str.replaceAll(".*img src=\"", "");
		final_str = final_str.replaceAll("\".*", "");
		final_title = final_title.replaceAll(".*.::", "");
		final_title = final_title.replaceAll("</title>.*", "");
		strip.setTitle("Ménage à 3 :" + final_title);
		return final_str;
	}

}
