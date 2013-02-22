package com.blogspot.applications4android.comicreader.core;

/**
 * Data structure to represent a comic-class read from json file
 */
public class ComicClass {
	/** class name of this comic */
	public String mClass;
	/** name of this comic */
	public String mName;
	/** preference-name of this comic */
	public String mPref;
	/** newly added comic or not */
	public boolean mNew;
	/** comic is selected or not */
	public boolean mSel;
	/** num unread strips */
	public int mUnread;
	/** can be displayed in the main list or not */
	public boolean mCanDisplay;

	/**
	 * Constructor
	 */
	public ComicClass() {
		mClass = null;
		mName = null;
		mPref = null;
		mNew = false;
		mSel = false;
		mUnread = 0;
		mCanDisplay = true;
	}
}
