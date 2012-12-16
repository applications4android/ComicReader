package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Bound;
import com.blogspot.applications4android.comicreader.core.Downloader;
import com.blogspot.applications4android.comicreader.core.Strip;


public class BugComic extends ArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.bugcomic.com/";
	}

	@Override
	protected String[] getAllComicUrls(BufferedReader reader) throws IOException {
		int idx = 0;
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str,str_temp;
		int i;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("class=\"archive-title\"");
			if (i != -1) {
				str_temp = str;
				str_temp=str_temp.replaceAll(".*href=\"","");
				str_temp=str_temp.replaceAll("\".*","");
				m_com.add(str_temp);
				idx++;
			}
		}
		String []m_com_urls = new String[idx];
		m_com.toArray(m_com_urls);
	    int left, right;
	    String temp;
		for (left=0, right=m_com_urls.length-1; left<right; left++, right--) {
		    temp = m_com_urls[left]; 
		    m_com_urls[left]  = m_com_urls[right]; 
		    m_com_urls[right] = temp;
		}
		return m_com_urls;
	}

	/**
	 * Fetch all the comic urls. bugcomic.com now provides gzipped html files!
	 */
	@Override
	protected void fetchAllComicUrls() {
		if(mComicUrls == null) {
			try {
//				GZIPInputStream gzis = new GZIPInputStream(Downloader.openConnectionStream(new URI(getArchiveUrl())));
//				BufferedReader reader = new BufferedReader(new InputStreamReader(gzis, "UTF-8"));
				BufferedReader reader = Downloader.openConnection(new URI(getArchiveUrl()));
				mComicUrls = getAllComicUrls(reader);
				reader.close();
			}
			catch(Exception e) {
				e.printStackTrace();
				return;
			}
			mLatestId = mComicUrls.length - 1;
			mBound = new Bound(0, (long) (mComicUrls.length - 1));
		}
	}

	@Override
	protected String getArchiveUrl() {
		return "http://www.bugcomic.com/archives/";
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	/**
	 * Main function responsible for downloading and reading a html page and returning the strip url. bugcomic.com now provides gzipped html files!
	 * @param url html page url
	 * @param strip the strip where to store the info
	 * @return the strip url
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	@Override
	public String parse(String url, Strip strip) throws ClientProtocolException, IOException, URISyntaxException {
		if(!htmlNeeded()) {
			return parse(url, null, strip);
		}
//		GZIPInputStream gzis = new GZIPInputStream(Downloader.openConnectionStream(new URI(url)));
//		BufferedReader br = new BufferedReader(new InputStreamReader(gzis, "UTF-8"));
		BufferedReader br = Downloader.openConnection(new URI(url));
		try {
			String surl = parse(url, br, strip);
			br.close();
			return surl;
		}
		catch(IOException e) {
			br.close();
			throw e;
		}
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip) throws IOException  {
		String str;
		String final_str = null;
		String final_title = null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf("class=\"comicpane\"");
			if (index1 != -1) {
				final_str = str;
				final_title = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_title = final_title.replaceAll(".*title=\"","");
		final_title = final_title.replaceAll("\".*","");
		strip.setTitle("Bug Comic: "+final_title); 
		strip.setText("-NA-");
	    return final_str;
	}
}
