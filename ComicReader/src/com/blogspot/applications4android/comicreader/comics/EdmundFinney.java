package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;

public class EdmundFinney extends ArchivedComic {

	@Override
	protected String[] getAllComicUrls(BufferedReader reader)
			throws IOException {
		
		ArrayList<String> strips = new ArrayList<String>();
		String line = reader.readLine();
		
		Pattern archiveStripPattern = Pattern.compile("http://eqcomics.com/..../../../.*?/");
		
		while((line != null)) {
			
			Matcher lineMatcher = archiveStripPattern.matcher(line);
			if(line.contains("<td class=\"archive-title\">") && lineMatcher.find()) {
				strips.add(lineMatcher.group(0));
				//Log.d("edmundfinney", "added strip url: "+lineMatcher.group(0));
			}
			
			line = reader.readLine();
		}
		
		Collections.reverse(strips);
		
		return strips.toArray(new String[strips.size()]);
	}

	@Override
	protected String getArchiveUrl() {
		return "http://eqcomics.com/archive";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://eqcomics.com";
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		
		String line   = reader.readLine();
		String imgUrl = null;
		String title  = null;
		
		Pattern imgPattern = Pattern.compile("http://eqcomics.com/comics/.*(jpg|png|gif)");
		
		Log.d("edmundfinney","parsing url: "+url);
		
		while((line != null)) {
			
			Matcher urlMatcher = imgPattern.matcher(line);

			if(urlMatcher.find()) {
				imgUrl = urlMatcher.group(0);
				//Log.d("edmundfinney", "found img url:"+imgUrl);
			}
			
			if(line.matches(".*<title>.*")) {
				title = line.replaceAll(".*<title>", "");
				title = title.replaceAll("</title>", "");
				title = title.replaceAll("Edmund Finney&#039;s Quest to Find the Meaning of Life - ", "");
				Log.d("edmundfinney", "found strip title: "+title);
			}
			
			line = reader.readLine();
		}
		
		strip.setText("");
		strip.setTitle(title);
		
		return imgUrl;
	}

}
