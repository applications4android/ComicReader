package com.blogspot.applications4android.comicreader.comics;

import android.content.res.AssetManager;
import android.test.InstrumentationTestCase;
import com.blogspot.applications4android.comicreader.core.Strip;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class WumoTest extends InstrumentationTestCase {

    private String url = "http://wumo.com/2013/07/06";

    public void testHtmlParse() throws Exception {
        String fileName = "ComicsTest/wumo.html";

        // basic setup
        AssetManager mgr = getInstrumentation().getContext().getAssets();
        Wumo wumo = new Wumo();
        Strip strip = new Strip(null, null);
        BufferedReader reader = new BufferedReader(new InputStreamReader(mgr.open(fileName)));

        String parse = wumo.parse(url, reader, strip);

        // parse method should return full url to strip image
        assertEquals("http://www.wumo.com/img/strip/2013/07/06.jpg", parse);
        assertEquals("Wumo: 2013/7/6", strip.getTitle());
    }

    public void testParseDateWithoutSlash() {
        Wumo wumo = new Wumo();
        Calendar timeFromUrl = wumo.getTimeFromUrl(url);
        verifyDate(timeFromUrl);
    }

    public void testParseDateWithSlashOnEnd() {
        Wumo wumo = new Wumo();
        Calendar timeFromUrl = wumo.getTimeFromUrl(url + "/");
        verifyDate(timeFromUrl);
    }

    private void verifyDate(Calendar timeFromUrl) {
        Calendar expectedDate = new GregorianCalendar(2013, Calendar.JULY, 6);
        assertEquals(expectedDate.get(Calendar.YEAR), timeFromUrl.get(Calendar.YEAR));
        assertEquals(expectedDate.get(Calendar.MONTH), timeFromUrl.get(Calendar.MONTH));
        assertEquals(expectedDate.get(Calendar.DAY_OF_MONTH), timeFromUrl.get(Calendar.DAY_OF_MONTH));
    }
}
