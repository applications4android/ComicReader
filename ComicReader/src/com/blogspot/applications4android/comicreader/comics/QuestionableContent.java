package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class QuestionableContent extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://www.questionablecontent.net";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.questionablecontent.net";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("/comics/");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
	   	final_str = final_str.replaceAll(".*comics/","");
		final_str = final_str.replaceAll(".png.*","");
	   	int finalid = Integer.parseInt(final_str);
	   	return finalid;
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.questionablecontent.net/view.php?comic=" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*comic=", ""));
	}

	@Override
	protected boolean htmlNeeded() {
		return false;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		int id = getIdFromStripUrl(url);
		/*
		String url_str = url.replaceAll(".*comic=", "");
		String url2 = "comics/" + url_str;
		String str,final_str=null;
		while((str = reader.readLine()) != null) {
			int index1 = str.indexOf(url2);
			if (index1 != -1) {
				final_str = str;
			}
		}
		final_str = final_str.replaceAll(".*comics/", "");
		final_str = final_str.replaceAll("\".*","");
		*/
		strip.setTitle("Questionable Content :"+ id);
		strip.setText("-NA-");
    	return "http://www.questionablecontent.net/comics/"+id+".png";
	}
}
