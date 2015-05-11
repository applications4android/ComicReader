package com.blogspot.applications4android.comicreader.comics;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.core.Strip;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import android.util.Log;


public class Wumo extends DailyComic {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("y/M/d");
    public static final Pattern URL_PATTERN = Pattern.compile(".*<img src=\"(.*)\" alt=.*");

    @Override
    protected Calendar getFirstCalendar() {
        Calendar first = Calendar.getInstance();
        first.set(2012, Calendar.DECEMBER, 6);
        return first;
    }

    @Override
    public String getComicWebPageUrl() {
        return "http://kindofnormal.com/wumo";
    }

    @Override
    protected Calendar getLatestCalendar() {
        return Calendar.getInstance(m_zone);
    }

    @Override
    protected Calendar getTimeFromUrl(String url) {
        int length = url.endsWith("/") ? 11 : 10;
        String str = url.substring(url.length() - length, url.length());
        String[] time = str.split("/");
        int year = Integer.parseInt(time[0]);
        int month = Integer.parseInt(time[1]) - 1;
        int day = Integer.parseInt(time[2]);
        Calendar date = Calendar.getInstance();
        date.set(year, month, day);
        return date;
    }

    @Override
    public String getUrlFromTime(Calendar cal) {
        return String.format("http://kindofnormal.com/wumo/%4d/%02d/%02d/",
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    protected boolean htmlNeeded() {
        return true;
    }

    @Override
    protected String parse(String url, BufferedReader reader, Strip strip)
            throws IOException {
        String str;
        String stripUrl = null;
        String title = null;
        while ((str = reader.readLine()) != null) {
            Matcher matcher = URL_PATTERN.matcher(str);
// We assume the first match on the page contains the stripUrl
            if (matcher.matches() && stripUrl == null ) {
               stripUrl = matcher.group(1);
               title = str.replaceAll(".* alt=\"", "Wumo: ");
               title = title.replaceAll("\".*", "");
            }
        }
//Log.d("Wumo", "stripUrl " + stripUrl);
        if (stripUrl == null) {
            throw new IOException("Wumo comic strip can't be found on url: " + url);
        }

//        strip.setTitle("Wumo: " + DATE_FORMAT.format(getTimeFromUrl(url).getTime()));
        strip.setTitle(title);
        strip.setText("-NA-");

        return stripUrl;
    }
}
