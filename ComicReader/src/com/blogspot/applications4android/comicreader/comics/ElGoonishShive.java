//Added by Crespyl

package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.comictypes.YearlyArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;

public class ElGoonishShive extends YearlyArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.egscomics.com/";
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
		
		Pattern imgPattern = Pattern.compile("comics/.*(jpg|png|gif)");
		
		Log.d("elgoonishshive","parsing url: "+url);
		
		while((line != null)) {
			
			Matcher urlMatcher = imgPattern.matcher(line);

			if(urlMatcher.find()) {
				imgUrl = "http://www.egscomics.com/"+urlMatcher.group(0);
			}
			
			if(line.matches(".*<title>.*")) {
				title = line.replaceAll(".*<title>", "");
				title = title.replaceAll("</title>", "");
				Log.d("elgoonishshive", "found strip title: "+title);
			}
			
			line = reader.readLine();
		}
		
		strip.setText("");
		strip.setTitle(title);
		
		return imgUrl;
	}

	@Override
	protected ArrayList<String> getAllComicUrls(BufferedReader reader, int year)
			throws IOException {
		
		Log.d("elgoonishshive","getting comic urls for year "+year);
		
		ArrayList<String> urls = new ArrayList<String>();
		Pattern comicUrlPattern = Pattern.compile("date=..........");
		
		String line = null;
		while((line = reader.readLine()) != null) {
			Matcher urlMatcher = comicUrlPattern.matcher(line);
			
			if(line.contains("index.php?date=")) {
				
				while(urlMatcher.find()) {
					urls.add("http://www.egscomics.com/index.php?"+urlMatcher.group());
					Log.d("elgoonishshive","found comic url: "+urlMatcher.group());
				}
			}
		}
		
		return urls;
		
	}

	@Override
	protected int getFirstYear() {
		return 2003;
	}

	@Override
	protected String getArchiveUrl(int year) {
		return String.format("http://www.egscomics.com/archives.php?year=%04d&start=0&displaymode=cal",
				year);
	}

	@Override
	protected boolean neededReversal() {
		return false;
	}

}
