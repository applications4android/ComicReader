package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import android.text.Html;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;

public class ZenPencils extends ArchivedComic {

	@Override
	protected String getArchiveUrl() {
		return "http://zenpencils.com/archives/";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://zenpencils.com/";
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String[] getAllComicUrls(BufferedReader reader)
			throws IOException {
		
		ArrayList<String> urls = new ArrayList<String>();
		String line;
		
		while ((line = reader.readLine()) != null) {
			if (line.contains("Permanent Link:")) {
				line = line.replaceAll(".*href=\"", "").replaceAll("\".*","");
				
				urls.add(line);
			}
		}
		
		int size = urls.size();
		String[] result = new String[size];
		
		for (String url : urls)
			result[--size] = url;
		
		return result;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		
		String title, image, line;
		
		while ((line = reader.readLine()) != null)
			if (line.contains("class=\"comicpane\""))
				break;
		
		if (line == null)
			throw new IOException("Could not find comic: " + url);
		
		image = line.replaceAll(".*img src=\"", "").replaceAll("\".*","");
		title = line.replaceAll(".*title=\"", "").replaceAll("\".*", "");
		
		strip.setTitle(title);
		strip.setText(findText(reader));
		
		return image;
	}

	private String findText(BufferedReader reader) throws IOException {
		
		String line;
		StringBuilder text = new StringBuilder();
		
		while ((line = reader.readLine()) != null) {
			if (line.contains("addthis_counter addthis_pill_style")) {
				reader.readLine(); //useless line
				
				while (!(line = reader.readLine()).contains("</div>")) {
					line = Html.fromHtml(line).toString().replaceAll("\"", "");
					text.append(line + "\n");
				}
				
				return text.toString();
			}
		}
		return null;
	}

}
