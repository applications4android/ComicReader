package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.NoSuchElementException;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import com.blogspot.applications4android.comicreader.comictypes.DailyComic;
import com.blogspot.applications4android.comicreader.core.Bound;
import com.blogspot.applications4android.comicreader.core.Downloader;
import com.blogspot.applications4android.comicreader.core.Strip;

import android.util.Log;

class CADStrip {
  public String mUrl;
  public String mTitle;
  public String mDate;
  public Calendar mCal;

  public CADStrip (String url, String title, String date, Calendar cal)
  {
    mUrl = url;
    mTitle = title;
    mDate = date;
    mCal = cal;
  }
};

public class CtrlAltDel extends DailyComic {
        protected SortedMap<String, TreeMap<Integer, CADStrip> > mMonths;
        protected Calendar mRefreshTime;

  	/**
	 * Constructor
	 */
	public CtrlAltDel() {
          super();
          mMonths = new TreeMap<String, TreeMap<Integer, CADStrip> >();
	}


        @Override
	public String getComicWebPageUrl() {
		return "https://www.cad-comic.com/cad/";
	}

        protected Calendar getFirstCalendar()
        {
          getMonths();
          String month = mMonths.firstKey();
          if (month == null) return null;
          SortedMap<Integer, CADStrip> dlist = getUrlsForMonth (month);
          int d = dlist.firstKey();
          int y = Integer.parseInt (month.substring (0, 4));
          int m = Integer.parseInt (month.substring (4, 6));
          Calendar c = Calendar.getInstance();
          c.set (y, m-1, d);
          return c;
        }


        protected Calendar getLatestCalendar()
        {
          getMonths();
          String month = mMonths.lastKey();
          if (month == null) return null;
          SortedMap<Integer, CADStrip> dlist = getUrlsForMonth (month);
          int d = dlist.lastKey();
          int y = Integer.parseInt (month.substring (0, 4));
          int m = Integer.parseInt (month.substring (4, 6));
          Calendar c = Calendar.getInstance();
          c.set (y, m-1, d);
          return c;
        }

  
        protected Calendar getTimeFromUrl(String url)
        {
          int i = url.lastIndexOf ("ENG_");
          if (i >= 0) {
            i += 4;
          }
          else {
            i = url.lastIndexOf ("sillies-");
            if (i >= 0) {
              i += 8;
            }
            else {
              i = url.lastIndexOf ("LITE_");
              if (i >= 0) {
                i += 5;
              }
              else {
                i = url.lastIndexOf ("Strip");
                if (i >= 0) {
                  i += 5;
                }
                else {
                  return null;
                }
              }
            }
          }
          int y = Integer.parseInt (url.substring (i,   i+4));
          int m = Integer.parseInt (url.substring (i+4, i+6));
          int d = Integer.parseInt (url.substring (i+6, i+8));
          Calendar c = Calendar.getInstance();
          c.set (y, m-1, d);
          return c;
        }


        private Calendar guessTimeFromUrl(String url)
        {
           // https://..../ENG_201811121.png
           int i = url.lastIndexOf ("ENG_");
           if (i >= 0) {
             i += 4;
           }
           else {
             i = url.lastIndexOf ("sillies-");
             if (i >= 0) {
               i += 8;
             }
             else {
               i = url.lastIndexOf ("cad-");
               if (i >= 0) {
                 i += 4;
               }
               else {
                 return null;
               }
             }
           }
           int y = Integer.parseInt (url.substring (i,   i+4));
           int m = Integer.parseInt (url.substring (i+4, i+6));
           int d = Integer.parseInt (url.substring (i+6, i+8));
           Calendar c = Calendar.getInstance();
           c.set (y, m-1, d);
           return c;
        }


        private String monthKey(Calendar cal)
        {
          int y = cal.get (Calendar.YEAR);
          int m = cal.get (Calendar.MONTH)+1;
          return String.format ("%04d%02d", y, m);
        }


        public String getUrlFromTime(Calendar cal)
        {
          CADStrip strip = getStripFromTime (cal);
          if (strip == null) return null;
          return strip.mUrl;
        }

	@Override
	protected boolean htmlNeeded() {
		return false;
	}

	@Override
	public void clearCache() {
          mMonths.clear();
          super.clearCache();
	}

	/**
	 * For those days which do not have a comic strip
	 * If the given daily comic series WAS irregular previously but now has been
	 * regular, then you have to override this function!
	 * @param in the calendar which needs to take care of this
	 * @param increment which date needs to be shown instead.
	 */
	public void addException(Calendar in, int increment)
        {
          if (getUrlFromTime (in) != null) return;

          int d = in.get (Calendar.DAY_OF_MONTH);
          String month = monthKey (in);
          SortedMap<Integer, CADStrip> l = getUrlsForMonth (month);
          if (l == null) return;

          if (increment > 0) {
            SortedMap<Integer, CADStrip> tail = l.tailMap (d);
            try {
              d = tail.firstKey();
              in.set (Calendar.DAY_OF_MONTH, d);
              return;
            }
            catch (NoSuchElementException e) {
            }

            in.add (Calendar.MONTH, 1);
            month = monthKey (in);
            l = getUrlsForMonth (month);
            if (l == null) return;
            try {
              d = l.firstKey();
              in.set (Calendar.DAY_OF_MONTH, d);
              return;
            }
            catch (NoSuchElementException e) {
            }
          }
          else { // increment < 0
            SortedMap<Integer, CADStrip> tail = l.headMap (d);
            try {
              d = tail.lastKey();
              in.set (Calendar.DAY_OF_MONTH, d);
              return;
            }
            catch (NoSuchElementException e) {
            }

            in.add (Calendar.MONTH, -1);
            month = monthKey (in);
            l = getUrlsForMonth (month);
            if (l == null) return;
            try {
              d = l.lastKey();
              in.set (Calendar.DAY_OF_MONTH, d);
              return;
            }
            catch (NoSuchElementException e) {
            }
          }
	}


        public CADStrip getStripFromTime(Calendar cal)
        {
          while (true) {
            int d = cal.get (Calendar.DAY_OF_MONTH);
            String month = monthKey (cal);
            getMonths();
            SortedMap<Integer, CADStrip> l = getUrlsForMonth (month);
            if (l == null) {
              return null;
            }
            CADStrip strip = l.get (d);
            if (strip != null) {
              return strip;
            }
            return null;
          }
        }

        protected CADStrip getCADStripFromUrl(String url)
        {
          Calendar c = getTimeFromUrl (url);
          return getStripFromTime (c);
        }


	protected String parse(String url, BufferedReader reader, Strip strip) throws IOException
        {
          CADStrip s = getCADStripFromUrl (url);
          strip.setTitle ("CtrlAltDel for " + s.mDate + ": " + s.mTitle);
          return url;
        }

        ///////

        // List of dates in the form YYYYMM.
        protected SortedMap<String, TreeMap<Integer, CADStrip> > getMonths() {
          Calendar c = Calendar.getInstance();
          if (c.after (mRefreshTime)) {
            mMonths.clear();
          }

          if (mMonths.size() > 0) return mMonths;
          try {
            URI uri = new URI("https://cad-comic.com/wp-admin/admin-ajax.php");
            String data = "action=get_cat_dates&cat_id=all";
            String sdata = Downloader.downloadToString(uri, data);
            JSONObject root = new JSONObject(sdata);
            JSONArray months = root.getJSONArray("months");
            for (int i = 0; i < months.length(); i++) {
              JSONObject o = months.getJSONObject(i);
              mMonths.put (o.getString("post_date"), null);
            }
          }
          catch (Exception e) {
            e.printStackTrace();
          }

          mRefreshTime = Calendar.getInstance();
          mRefreshTime.add (Calendar.HOUR, 1);

          return mMonths;
        }

        protected SortedMap<Integer, CADStrip> getUrlsForMonth (String month)
        {
            getMonths();
            TreeMap<Integer, CADStrip> l = mMonths.get (month);
            try {
              if (l == null) {
                if (mMonths.containsKey (month)) {
                  l = new TreeMap<Integer, CADStrip>();
                  URI uri = new URI("https://cad-comic.com/wp-admin/admin-ajax.php");
                  String data = "action=custom_cat_search&post_cat=all&post_month="+month;
                  String sdata = Downloader.downloadToString(uri, data);
                  JSONObject root = new JSONObject(sdata);
                  JSONArray posts = root.getJSONArray("posts");
                  List<String> ret = new ArrayList<String>();
                  for (int i = 0; i < posts.length(); i++) {
                    JSONObject o = posts.getJSONObject(i);
                    String url = o.getString("comic");
                    if (url.lastIndexOf("KDM") >= 0) continue;
                    Calendar c = getTimeFromUrl (url);
                    if (!month.equals (monthKey (c))) {
                      continue;
                    }
                    int dom = c.get (Calendar.DAY_OF_MONTH);
                    CADStrip strip = new CADStrip (url, o.getString("title"), o.getString("date"), c);
                    l.put (dom, strip);
                  }
                  mMonths.put (month, l);
                }
              }
            }
            catch (Exception e)
            {
              e.printStackTrace();
            }
            return l;
        }
}
