package com.blogspot.applications4android.comicreader.comics.DailyKingFeatures;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.blogspot.applications4android.comicreader.comictypes.DailyKingFeaturesComic;
import com.blogspot.applications4android.comicreader.core.Strip;

public class Hagar extends DailyKingFeaturesComic {
    /** for logging purposes */
    protected static final String TAG = "hagarthehorrible";
    protected static final String STRIP_NAME = "Hagar the Horrible";

    @SuppressLint("SimpleDateFormat")
    private final DateFormat urlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final Pattern imageRegex = Pattern.compile("src=\"(http://safr.kingfeatures.com[^\"]*)\"");

    public Hagar() {
        super();
        mFirstCal = Calendar.getInstance();
        //Only the past year's worth of comics are available through comicskingdom
        mFirstCal.set(Calendar.YEAR, mFirstCal.get(Calendar.YEAR) - 1);
    }

    @Override
    public String getComicWebPageUrl() {
        return "http://comicskingdom.com/hagar-the-horrible/";
    }


    @Override
    public String getUrlFromTime(Calendar cal) {
        final String url = this.getComicWebPageUrl() + this.urlDateFormat.format(cal.getTime());
        Log.d(TAG, "getUrlFromTime: url=" + url);
        return url;
    }

    @Override
    protected Calendar getTimeFromUrl(String url) {
        String web = getComicWebPageUrl();
        url = url.replace(web, "");
        url = url.replaceAll("/", "");

        final Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(this.urlDateFormat.parse(url));
        } catch(ParseException e) {
            Log.e(TAG, "Error parsing date", e);
        }
        return cal;
    }

    @Override
    protected String parse(String url, BufferedReader reader, Strip strip) throws IOException {
        String str;
        String final_str = null;
        while ((str = reader.readLine()) != null) {
            final Matcher matcher = this.imageRegex.matcher(str);
            if(final_str == null && matcher.find()) {
                final_str = matcher.group(1);
            }
        }
        strip.setTitle(STRIP_NAME + ": " + _getTimeStringFromUrl(strip.uid()));
        strip.setText("-NA-");
        return final_str;
    }

    private String _getTimeStringFromUrl(String url) {
        return url.replaceAll(this.getComicWebPageUrl(), "");
    }
}
