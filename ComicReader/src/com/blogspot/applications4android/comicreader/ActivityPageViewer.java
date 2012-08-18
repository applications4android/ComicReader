package com.blogspot.applications4android.comicreader;


import com.blogspot.applications4android.comicreader.core.ComicActivity;

import android.os.Bundle;


/**
 * Generic activity to display only a webview widget.
 * To display a html page, just pass it in the 'extras' bundle
 * under the key named 'page'.
 */
public class ActivityPageViewer extends ComicActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	Bundle extras = getIntent().getExtras();
    	if(extras != null) {
    		String page = extras.getString("page");
    		displayHtml(R.layout.page_viewer, R.id.web_viewer, page);
    	}
    }

}
