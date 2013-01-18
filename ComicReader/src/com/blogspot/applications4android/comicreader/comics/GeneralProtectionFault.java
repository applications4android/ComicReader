package com.blogspot.applications4android.comicreader.comics;

import android.util.Log;
import com.blogspot.applications4android.comicreader.comictypes.YearlyArchivedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Osiris
 */
public class GeneralProtectionFault extends YearlyArchivedComic {

    @Override
    protected ArrayList<String> getAllComicUrls(BufferedReader reader, int year) throws IOException {
		ArrayList<String> m_com = new ArrayList<String>(); 
		String str, str_temp;
		int i, j;
		while((str = reader.readLine()) != null) {
			if (str.contains("<div class=\"calendar\">")) {
                            // one line per month
                            while ((i = str.indexOf("<a href=")) != -1 &&
                                   (j = str.indexOf("</a>")) != -1) {
                                str_temp = str.substring(i, j);
                                str = str.substring(j+4);
                                str_temp=str_temp.replaceAll(".*href=\"","");
				str_temp=str_temp.replaceAll("\".*","");
				m_com.add(str_temp);
                            }
                        }
		}
		return m_com;
    }

    @Override
    protected int getFirstYear() {
        return 1998;
    }

    @Override
    protected String getArchiveUrl(int year) {
        return "http://www.gpf-comics.com/archive/calendar.php?year=" + year;
    }

    @Override
    protected boolean neededReversal() {
        return false;
    }

    @Override
    public String getComicWebPageUrl() {
        return "http://www.gpf-comics.com/";
    }

    @Override
    protected boolean htmlNeeded() {
        return true;
    }

    @Override
    protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
        String str;
        String final_str = null;
        String final_title = null;
        while((str = reader.readLine()) != null) {
            int index1 = str.indexOf("<img src=\"/comics/");
            if (index1 != -1) {
                final_str = str;
                final_title = str;
                break;
            }
        }
        final_str = final_str.replaceAll(".*src=\"","");
        final_str = final_str.replaceAll("\".*","");
        final_str = "http://www.gpf-comics.com" + final_str;
        final_title = final_title.replaceAll(".*alt=\"","");
        final_title = final_title.replaceAll("\".*","");
        strip.setTitle("GPF: "+final_title); 
        strip.setText("-NA-");
        return final_str;
    }
}
