package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;


public class SpikedMath extends ArchivedComic {

	@Override
	public String getComicWebPageUrl() {
		return "http://www.spikedmath.com";
	}

	@Override
	protected String getArchiveUrl() {
		return "http://spikedmath.com/archives.html";
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
			int index1 = str.indexOf("alt=\"Spiked Math Comic");
			if (index1 != -1) {
				final_str = str;
			}
			int index2 = str.indexOf("Spiked Math</title>");
			if (index2 != -1) {
				final_title = str;
			}
		}
		final_str = final_str.replaceAll(".*src=\"","");
		final_str = final_str.replaceAll("\".*","");
		final_title = final_title.replaceAll(".*<title>","");
		final_title = final_title.replaceAll("</title.*","");
		strip.setTitle(final_title); 
		strip.setText("-NA-");
		return final_str;
	}

	@Override
	protected String[] getAllComicUrls(BufferedReader reader)
			throws IOException {
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str;
		boolean archivesFound = false;
		while((str = reader.readLine()) != null) {
			if(str.indexOf("archive-content") != -1) {
				archivesFound = true;
				continue;
			}
			if(str.indexOf("beta-inner") != -1) {
				archivesFound = false;
				continue;
			}
			if(archivesFound) {
				if(str.indexOf("<a href=\"") != -1) {
					String temp = str.replaceAll(".*<a href=\"", "");
					temp = temp.replaceAll("\".*", "");
					m_com.add(temp);
				}
			}
		}
		Collections.reverse(m_com);
		String[] m_com_urls = new String[m_com.size()];
		m_com.toArray(m_com_urls);
		return m_com_urls;
	}
}
