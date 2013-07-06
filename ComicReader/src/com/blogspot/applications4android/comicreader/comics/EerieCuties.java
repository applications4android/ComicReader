package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;


import com.blogspot.applications4android.comicreader.comictypes.ArchivedComic;
import com.blogspot.applications4android.comicreader.core.Bound;
import com.blogspot.applications4android.comicreader.core.Downloader;
import com.blogspot.applications4android.comicreader.core.Strip;

public class EerieCuties extends ArchivedComic {

	@Override
	protected String[] getAllComicUrls(BufferedReader reader)
			throws IOException {
		int idx = 0;
		ArrayList<String> m_com = new ArrayList<String>(); 
		ArrayList<String> m_com0 = new ArrayList<String>(m_com.size());
		String str,str_temp;
		int i,j;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("><a href=\"http://www.eeriecuties.com/strips-ec/");
			if (i != -1) {
				str_temp = str;
				str_temp=str_temp.replaceAll(".*?href=\"","");
				int index = str_temp.indexOf("src=\"http://ace.eeriecuties.com/comics/tn/");
				if(index>0){
					str_temp = str_temp.substring(0,index-7);
				}
				m_com.add(str_temp);
				idx++;
			}
		}//following code is because the original order of URL list is out of order
		int remainder = m_com.size()%3;
		if(remainder==0){
			for(j=0;j<m_com.size()/3;j++){
				m_com0.add(j*3, m_com.get(j));
				m_com0.add(j*3+1, m_com.get(m_com.size()/3+j));
				m_com0.add(j*3+2, m_com.get(m_com.size()*2/3+j));
			}
		}
		if(remainder==1){
			for(j=0;j<(m_com.size()-1)/3;j++){
				m_com0.add(j*3, m_com.get(j));
				m_com0.add(j*3+1, m_com.get((m_com.size()-1)/3+1+j));
				m_com0.add(j*3+2, m_com.get((m_com.size()-1)*2/3+1+j));
			}
			m_com0.add(m_com.size()-1,m_com.get((m_com.size()-1)/3));
		}
		if(remainder==2){
			for(j=0;j<(m_com.size()-2)/3;j++){
				m_com0.add(j*3, m_com.get(j));
				m_com0.add(j*3+1, m_com.get((m_com.size()-2)/3+1+j));
				m_com0.add(j*3+2, m_com.get((m_com.size()-2)*2/3+2+j));
			}
			m_com0.add(m_com.size()-2,m_com.get((m_com.size()-2)/3));
			m_com0.add(m_com.size()-1,m_com.get((m_com.size()-2)*2/3+1));
		}
		String[] m_com_urls = new String[idx];
		m_com0.toArray(m_com_urls);
		return m_com_urls;
	}
    
	@Override
	protected void fetchAllComicUrls() {
		ArrayList<String> all_vols = new ArrayList<String>();
		if (mComicUrls == null) {
			try {
				// Archive has volumes at different URL so get all source URLs
				ArrayList<String> vol_urls = getVolURL();
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

	private ArrayList<String> getVolURL() {
		ArrayList<String> vol_urls = new ArrayList<String>();
		vol_urls.add("http://www.eeriecuties.com/archive/volume0");
		vol_urls.add("http://www.eeriecuties.com/archive/volume1");
		vol_urls.add("http://www.eeriecuties.com/archive/volume2");
		vol_urls.add("http://www.eeriecuties.com/archive/volume3");
		return vol_urls;
	}
	
	@Override
	protected String getLatestStripUrl() {
		fetchAllComicUrls();
		return getStripUrlFromId(mComicUrls.length - 1);
	}
	
	@Override
	protected String getArchiveUrl() {
		return "http://www.eeriecuties.com/archive";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.eeriecuties.com/";
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String url_link = null;
		String str_title = null;
		String str,str_temp;
		int i;
		while((str = reader.readLine()) != null) {
			i = str.indexOf("http://ace.eeriecuties.com/comics/");
			if (i != -1) {
				str_temp = str;
				str_temp=str_temp.replaceAll(".*?src=\"","");
				str_temp=str_temp.replaceAll(".png.*","");
				str_temp=str_temp.replaceAll(".jpg.*","");
				int res = 0;
				for (int j=0; j < str_temp.length(); j++) {
				    char c = str_temp.charAt(j);
				    if (c < '0' || c > '9'){
				    }else{
				    int cc = Character.getNumericValue(c);
				    res = res * 10 + cc;}
				}
				if(res<20101102){
				url_link = str_temp + ".jpg";
			}else{
				url_link = str_temp + ".png";
			}
			}
		}
		str_title = url.replaceAll(".*?strips-ec/","");
		str_title = str_title.replaceAll("%21","!");
		str_title = str_title.replaceAll("%28","(");
		str_title = str_title.replaceAll("%29",")");
		str_title = "Eerie Cutie: "+str_title;
		strip.setTitle(str_title);
		strip.setText("-NA-");
		return url_link;
	}

}
