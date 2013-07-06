package com.blogspot.applications4android.comicreader.comics;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.core.Strip;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Wumo extends DailyComic {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("y/M/d");
    public static final Pattern URL_PATTERN = Pattern.compile("\\s*<img src=\"(.*)\" alt=\"Daily strip.*");

    @Override
    protected Calendar getFirstCalendar() {
        Calendar first = Calendar.getInstance();
        first.set(2003, Calendar.AUGUST, 4); // 2003/08/04/
        return first;
    }

    @Override
    public String getComicWebPageUrl() {
        return "http://www.wumo.com";
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
        return String.format("http://www.wumo.com/%4d/%02d/%02d/",
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
        while ((str = reader.readLine()) != null) {
            Matcher matcher = URL_PATTERN.matcher(str);
            if (matcher.matches()) {
                stripUrl = matcher.group(1);
            }
        }
        if (stripUrl == null) {
            throw new IOException("Wumo comic strip can't be found on url: " + url);
        }

        strip.setTitle("Wumo: " + DATE_FORMAT.format(getTimeFromUrl(url).getTime()));
        strip.setText("-NA-");

        return getComicWebPageUrl() + stripUrl;
    }
}
