package com.blogspot.applications4android.comicreader;

import java.io.File;

import com.blogspot.applications4android.comicreader.core.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity to save the strip onto sdcard on user's request
 * Pass the source file in 'src_file' and destination in 'dest_file'
 */
public class ActivitySaveStrip extends Activity {
	/** file where to save the strip */
	private String mDestFile;
	/** file from where to save the strip */
	private String mSrcFile;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_strip);
    	Bundle extras = getIntent().getExtras();
    	if(extras != null) {
    		mSrcFile = extras.getString("src_file");
    		mDestFile = extras.getString("dest_file");
    	}
    	else {
    		Toast.makeText(getApplicationContext(),
    				"You did not pass 'file' in the extras for this intent!",
    				Toast.LENGTH_SHORT).show();
    		finish();
    	}
    	TextView dis = (TextView) findViewById(R.id.ss_disclaimer);
    	dis.setText(_getDisclaimer());
    	EditText et = (EditText)  findViewById(R.id.ss_location);
    	et.setText(mDestFile);
    }

    /**
     * Callback for saving the strip
     * @param v view which is calling back
     */
    public void saveStrip(View v) {
    	EditText et = (EditText)  findViewById(R.id.ss_location);
    	mDestFile = et.getText().toString();
    	File src = new File(mSrcFile);
    	File dest = new File(mDestFile);
    	String msg;
    	if(FileUtils.copyFile(src, dest)) {
    		msg = "Successfully saved the strip to '"+mDestFile+"'";
    	}
    	else {
    		msg = "Failed to store the strip to '"+mDestFile+"'";
    	}
    	Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    	finish();
    }

    /**
     * Callback for cancelling saving the strip
     * @param v view which is calling back
     */
    public void cancelSaveStrip(View v) {
    	String msg = "Save strip has been cancelled";
    	Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    	finish();
    }

    /**
     * Returns the disclaimer associated with saving the strips
     * @return disclaimer as html formatted string
     */
    private Spanned _getDisclaimer() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("<b>DISCLAIMER:</b><br>");
    	sb.append("1. Authors of this app are not the authors of this strip.<br>");
    	sb.append("2. Authors of this app are in no way responsible for misusing the strips saved from here.<br>");
    	sb.append("3. Strips saved from here are for personal use only.<br>");
    	sb.append("4. By pressing 'OK' below, you are agreeing to the above points.<br>");
    	sb.append("5. By pressing 'OK' below, you are agreeing to the copyright/licenses associated with this strip.<br>");
    	Spanned data = Html.fromHtml(sb.toString());
    	return data;
    }
}
