package com.blogspot.applications4android.comicreader.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;


/**
 * Base class for parsing html files to get the strip info
 */
public abstract class ComicParser {

	/**
	 * Main function responsible for downloading and reading a html page and returning the strip url
	 * @param url html page url
	 * @param strip the strip where to store the info
	 * @return the strip url
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public String parse(String url, Strip strip) throws ClientProtocolException, IOException, URISyntaxException {
		if(!htmlNeeded()) {
			return parse(url, null, strip);
		}
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

	/**
	 * Whether one needs to parse the html file in order to find the strip url
	 * @return true if html is needed, else false
	 */
	protected abstract boolean htmlNeeded();

	/**
	 * Function to read every line from the reader and extract strip-info
	 * @param url html page url
	 * @param reader reader
	 * @param strip the strip where to store the info
	 * @return the strip url
	 * @throws IOException
	 */
	protected abstract String parse(String url, BufferedReader reader, Strip strip) throws IOException;

}
