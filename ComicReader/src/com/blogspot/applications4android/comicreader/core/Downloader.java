package com.blogspot.applications4android.comicreader.core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.blogspot.applications4android.comicreader.exceptions.ComicSDCardFull;

import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;

import android.util.Log;

/**
 * Class containing utility functions for downloading the URI's
 */
public class Downloader {
	/** buffer size for downloading the string (in B) */
	private static final int DNLD_BUFF_SIZE = 8192;
	/** string builder capacity (in B) */
	private static final int SB_CAPACITY = 8192;


	/**
	 * Private constructor!
	 */
	private Downloader() {
	}

	/**
	 * Helper function to download a URI from the given url and save it to a string.
	 * Uses URLConnection in order to set up a connection with the server and download
	 * the URI requested.
	 * @param uri The url of interest.
	 * @return the contents of the url in a string
	 * @throws IOException 
	 * @throws URISyntaxException 
	 * @throws ClientProtocolException 
	 */
	public static String downloadToString(URI uri) throws ClientProtocolException, URISyntaxException, IOException {
            return downloadToString(uri, "");
	}

	/**
	 * Helper function to download a URI from the given url and save it to a string.
	 * Uses URLConnection in order to set up a connection with the server and download
	 * the URI requested.
	 * @param uri The url of interest.
         * @parma data Data to POST, or empty for a GET.
	 * @return the contents of the url in a string
	 * @throws IOException 
	 * @throws URISyntaxException 
	 * @throws ClientProtocolException 
	 */
         public static String downloadToString(URI uri, String data) throws ClientProtocolException, URISyntaxException, IOException {
                BufferedReader br = openConnection(uri, data);
		StringBuilder sb = new StringBuilder(SB_CAPACITY);
		char[] buff = new char[SB_CAPACITY];
		int len;
	    while((len = br.read(buff)) > 0) {
	    	sb.append(buff, 0, len);
	    }
	    br.close();
	    return sb.toString();
	}

	/**
	 * Open a http connection with the given url
	 * @param uri desired url
	 * @return buffered reader for this url connection
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static BufferedReader openConnection(URI uri) throws URISyntaxException, ClientProtocolException, IOException {
                return openConnection(uri, "");
	}

	/**
	 * Open a http connection with the given url
	 * @param uri desired url
         * @parma data Data to POST, or empty for a GET.
	 * @return buffered reader for this url connection
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
         public static BufferedReader openConnection(URI uri, String data) throws URISyntaxException, ClientProtocolException, IOException {
           /*
		HttpGet http = new HttpGet(uri);
		HttpClient client = new DefaultHttpClient();
		HttpResponse resp = (HttpResponse) client.execute(http);
		HttpEntity entity = resp.getEntity();
		InputStreamReader isr = new InputStreamReader(entity.getContent());
           */
           
                URL url = uri.toURL();
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setInstanceFollowRedirects (true);
                if (data.length() > 0) {
                  con.setRequestMethod("POST");
                  con.setDoOutput(true);
                  con.getOutputStream().write(data.getBytes("UTF-8"));
                }
		InputStreamReader isr = new InputStreamReader(con.getInputStream());
           
		BufferedReader br = new BufferedReader(isr, DNLD_BUFF_SIZE);
		return br;
	}

	/**
	 * Open a http connection with the given url
	 * @param uri desired url
	 * @return buffered reader stream for this url connection
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static BufferedInputStream openConnectionStream(URI uri) throws URISyntaxException, ClientProtocolException, IOException {
                URL url = uri.toURL();
                URLConnection con = url.openConnection();
                InputStream is = con.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(is);
		return bis;
	}

	/**
	 * Helper function to download the image from the given uri into a local file
	 * @param file local file to store the image
	 * @param uri url from which to download the image
	 * @throws ComicSDCardFull
	 * @throws ClientProtocolException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static void dnldImage(String file, URI uri) throws ComicSDCardFull, ClientProtocolException, URISyntaxException, IOException {
		FileUtils.checkFreeSpaceSdcard();
		FileOutputStream fos = new FileOutputStream(file);
		BufferedInputStream reader = openConnectionStream(uri);
        byte[] buff = new byte[DNLD_BUFF_SIZE];
        int len;
        while((len = reader.read(buff, 0, DNLD_BUFF_SIZE)) > 0) {
            fos.write(buff, 0, len);
        }
        fos.close();
        reader.close();
	}

	/**
	 * Replaces all sort of '&#39;' kind of occurences in HTML strings with their corresponding ASCII chars.
	 * @param in the input HTML string
	 * @return the ASCII-converted string
	 */
	public static String decodeHtml(String in) {
		if(in == null) {
			return in;
		}
		String out = in;
		String expr, repl;
		// HACK HACK HACK!!!
		// need to look for a sane way of doing this...
		for(int i=33;i<=47;i++) {
			char a = (char) i;
			expr = "&#" + i + ";";
			repl = "" + a;
			out = out.replaceAll(expr, repl);
		}
		out = out.replaceAll("&#8208;", "-");
		out = out.replaceAll("&#8211;", "-");
		out = out.replaceAll("&#8216;", "'");
		out = out.replaceAll("&#8217;", "'");
		out = out.replaceAll("&#8218;", ",");
		out = out.replaceAll("&#8219;", "'");
		out = out.replaceAll("&#8220;", "\"");
		out = out.replaceAll("&#8221;", "\"");
		out = out.replaceAll("&#8223;", "\"");
		out = out.replaceAll("&#8230;", "...");
		out = out.replaceAll("&quot;", "\"");
		out = out.replaceAll("&lt;", "<");
		out = out.replaceAll("&gt;", ">");
		out = out.replaceAll("&#amp;", "&");
		return out;
	}

}
