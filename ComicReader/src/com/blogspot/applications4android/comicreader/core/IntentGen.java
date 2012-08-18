package com.blogspot.applications4android.comicreader.core;

import java.net.URL;

import android.content.Intent;
import android.net.Uri;
import android.text.Html;


/**
 * Helper class to generate emails
 */
// TODO: add unit-tests
public class IntentGen {

	/**
	 * Constructor (private!)
	 */
	private IntentGen() {
	}

	/**
	 * Generates an email chooser Intent and returns it back
	 * @param to list of 'to' addresses
	 * @param subj subject of the email
	 * @param body body of the email
	 * @param html whether or not the body is html
	 * @param title title of the created chooser intent
	 * @return the desired intent using which one can 'startActivity'
	 */
	public static Intent emailChooserIntent(String[] to, String subj, String body, boolean html, String title) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("plain/text");
		i.putExtra(Intent.EXTRA_EMAIL, to);
		i.putExtra(Intent.EXTRA_SUBJECT, subj);
		if(html) {
			i.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));
		}
		else {
			i.putExtra(Intent.EXTRA_TEXT, body);
		}
		Intent chooser = Intent.createChooser(i, title);
		return chooser;
	}

	/**
	 * Generates a generic chooser Intent for sharing links and returns it back
	 * @param subj subject for the share
	 * @param link link to be shared
	 * @param title title of the created chooser intent
	 * @return the desired intent using which one can 'startActivity'
	 */
	public static Intent shareLinkChooserIntent(String subj, URL link, String title) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT, subj);
		i.putExtra(Intent.EXTRA_TEXT, link.toExternalForm());
		Intent chooser = Intent.createChooser(i, title);
		return chooser;
	}

	/**
	 * Generates a generic chooser Intent for sharing links and returns it back
	 * @param subj subject for the share
	 * @param link link to be shared
	 * @param title title of the created chooser intent
	 * @return the desired intent using which one can 'startActivity'
	 */
	public static Intent shareLinkChooserIntent(String subj, String link, String title) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_SUBJECT, subj);
		i.putExtra(Intent.EXTRA_TEXT, link);
		Intent chooser = Intent.createChooser(i, title);
		return chooser;
	}

	/**
	 * Generates an intent for viewing a url
	 * @param link url to be viewed
	 * @return desired intent using which one can 'startActivity'
	 */
	public static Intent linkViewIntent(String link) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		Uri u = Uri.parse(link);
		i.setData(u);
		return i;
	}
}
