package com.blogspot.applications4android.comicreader.comics;

import android.util.Log;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.core.Strip;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MFazio on 2015-04-08.
 */
public class Bizarro extends DailyComic {

    private static final String TAG = "Bizarro";

    private final Calendar mFirstCal;
    private Calendar mLatestCal = null;

    private final DateFormat bizarroDateFormat = new SimpleDateFormat("MMMM-d-yyyy", Locale.US);
    private final DateFormat comicReaderDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    private final Pattern imageRegex = Pattern.compile("<meta property=\"og:image\" content=\"([^\"]*)\"/>");

    public Bizarro() {
        super();
        mFirstCal = Calendar.getInstance();
        //TODO: This is the first daily comic; previously this was only available on Sundays
        mFirstCal.set(2002, Calendar.DECEMBER, 29);
    }

    @Override
    protected Calendar getFirstCalendar() {
        return mFirstCal;
    }

    @Override
    protected Calendar getLatestCalendar() {
        if(mLatestCal != null) {
            return mLatestCal;
        }
        mLatestCal = Calendar.getInstance(m_zone);
        return mLatestCal;
    }

    @Override
    protected Calendar getTimeFromUrl(String url) {
        final Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(this.bizarroDateFormat.parse(this._getTimeStringFromUrl(url)));
        } catch(ParseException e) {
            Log.e(TAG, "Error parsing date from URL", e);
        }

        return calendar;
    }

    @Override
    public String getUrlFromTime(Calendar cal) {
        return this.getComicWebPageUrl() + this.bizarroDateFormat.format(cal.getTime());
    }

    @Override
    public String getComicWebPageUrl() {
        return "http://bizarro.com/comics/";
    }

    @Override
    protected boolean htmlNeeded() {
        return true;
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
        strip.setTitle(this.getClass().getSimpleName() + ": " + _getFormattedTimeStringFromUrl(strip.uid()));
        strip.setText("-NA-");
        return final_str;
    }

    private String _getTimeStringFromUrl(final String url) {
        return url.replaceAll(this.getComicWebPageUrl(), "").replaceAll("/", "");
    }

    private String _getFormattedTimeStringFromUrl(final String url) {
        final Calendar urlTime = this.getTimeFromUrl(url);

        return this.comicReaderDateFormat.format(urlTime.getTime());
    }
}
