package com.blogspot.applications4android.comicreader.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.blogspot.applications4android.comicreader.exceptions.ComicNotFoundException;
import com.blogspot.applications4android.comicreader.exceptions.ComicSDCardFull;
import com.blogspot.applications4android.comicreader.exceptions.ComicSortException;

import android.content.res.AssetManager;
import android.util.Log;


/**
 * Class which manages the list of comics
 */
public class ComicClassList {
	/** json file containing the list of supported classes */
	public static final String CLASSES = "classes.json";
	/** json file containing the list of selected classes */
	public static final String SELECTED = "selected.json";

	/** sorting in ASCII order */
	public static final int SORT_ALPHABETICAL = 1;
	/** sorting in ASCII order but ignoring the articles */
	public static final int SORT_IGNORE_ARTICLES = 2;
	/** sorting with editorial comics at the last */
	public static final int SORT_EDITORIAL_LAST = 3;
	/** sorting in newly added first order */
	public static final int SORT_NEWEST_FIRST = 4;
	/** sorting in selected first order */
	public static final int SORT_SELECTED_FIRST = 5;

	/** for logging purposes only */
	private static final String TAG = "ComicClassList";

	/** package to which all these classes are part of */
	private String mPkg;
	/** list of all comics read from json file */
	private ComicClass[] mClasses;
	/** hashmap for these classes */
	private HashMap<String, Integer> mIdxs;
	/** file which stores the selected comics list */
	private File mSel;

	//// NOTE NOTE NOTE NOTE ////
	/// Before releasing to market, do remember to set the below flag to 'true'!!!!!!!!
	/** flag which will tell whether to use the below array to filtering the comics! */
	private static boolean FILTER_COMICS = false;

	/** list of comics that cannot be shown to the users! */
	private static final String[] FILTER_COMICS_LIST = { "Cyanide and Happiness" };


	/**
	 * Constructor
	 * @param mgr the asset manager
	 * @throws JSONException 
	 * @throws IOException 
	 * @throws ComicNotFoundException 
	 */
	public ComicClassList(AssetManager mgr) throws IOException, JSONException, ComicNotFoundException {
		File sel = new File(FileUtils.getComicRoot(), SELECTED);
		_init_(mgr, CLASSES, sel);
	}

	/**
	 * Constructor
	 * @param mgr asset manager
	 * @param path path to json file wrt assets
	 * @throws JSONException 
	 * @throws IOException 
	 * @throws ComicNotFoundException 
	 */
	public ComicClassList(AssetManager mgr, String path) throws IOException, JSONException, ComicNotFoundException {
		File sel = new File(FileUtils.getComicRoot(), SELECTED);
		_init_(mgr, path, sel);
	}

	/**
	 * Constructor
	 * @param mgr asset manager
	 * @param path path to json file wrt assets
	 * @param selected file containing the list of selected comics
	 * @throws JSONException 
	 * @throws IOException 
	 * @throws ComicNotFoundException 
	 */
	public ComicClassList(AssetManager mgr, String path, File selected) throws IOException, JSONException, ComicNotFoundException {
		_init_(mgr, path, selected);
	}

	/**
	 * Gets the package to which all the classes belong to
	 * @return package name
	 */
	public String getPackage() {
		return mPkg;
	}

	/**
	 * Gets the number of comics in the list
	 * @return number of comics
	 */
	public int length() {
		return mClasses.length;
	}

	/**
	 * Sort the classes according to the pre-defined types
	 * @param type sorting type
	 * @throws ComicSortException 
	 */
	public void sortClasses(int type) throws ComicSortException {
		if(mClasses.length <= 0) {
			Log.d(TAG, "sortClasses: nothing to sort...");
			return;
		}
		switch(type) {
		case SORT_ALPHABETICAL:
			Arrays.sort(mClasses, new AsciiComparator());
			break;
		case SORT_IGNORE_ARTICLES:
			Arrays.sort(mClasses, new IgnoreArticlesComparator());
			break;
		case SORT_EDITORIAL_LAST:
			Arrays.sort(mClasses, new EditorialLastComparator());
			break;
		case SORT_NEWEST_FIRST:
			Arrays.sort(mClasses, new NewestFirstComparator());
			break;
		case SORT_SELECTED_FIRST:
			Arrays.sort(mClasses, new SelectedFirstComparator());
			break;
		default:
			throw new ComicSortException("Bad sort-type passed! type="+type);
		}
		int i = 0;
		for(ComicClass clz : mClasses) {
			mIdxs.put(clz.mName, i);
			++i;
		}
	}

	/**
	 * get the comic class object from the index
	 * @param idx index
	 * @return comic class object
	 * @throws ComicNotFoundException
	 */
	public ComicClass getComicClassFromIndex(int idx) throws ComicNotFoundException {
		if((idx < 0) || (idx >= mClasses.length)) {
			ComicNotFoundException cnf = new ComicNotFoundException("Bad Index passed for 'getComicFromIndex'! idx="+idx+"len="+mClasses.length);
			throw cnf;
		}
		return mClasses[idx];
	}

	/**
	 * Get the comic object from the index
	 * @param idx comic index
	 * @return comic object
	 * @throws ComicNotFoundException
	 */
	// TODO: add unit-tests
	public Comic getComicFromIndex(int idx) throws ComicNotFoundException {
		if((idx < 0) || (idx >= mClasses.length)) {
			ComicNotFoundException cnf = new ComicNotFoundException("Bad Index passed for 'getComicFromIndex'! idx="+idx+"len="+mClasses.length);
			throw cnf;
		}
		try {
			String fname = mPkg + "." + mClasses[idx].mClass;
			Log.d(TAG, "Trying to load class="+fname);
			Class<?> comicClass = Class.forName(fname);
			Comic com = (Comic)comicClass.newInstance();
			com.setComicName(mClasses[idx].mName);
			Log.d(TAG, "Successfully loaded class="+fname);
			return com;
		}
		catch(Exception e) {
			ComicNotFoundException cnf = new ComicNotFoundException("Comic for idx="+idx+" could not be found!");
			throw cnf;
		}
	}

	/**
	 * Get the comic object from the title
	 * @param title comic english name
	 * @return comic object
	 * @throws ComicNotFoundException
	 */
	// TODO: add unit-tests
	public Comic getComicFromTitle(String title) throws ComicNotFoundException {
		Log.d(TAG, "getting comic for title="+title);
		return getComicFromIndex(getComicIndexFromTitle(title));
	}

	/**
	 * Get the comic next to the one wrt the input title
	 * @param title comic english name
	 * @return comic object
	 * @throws ComicNotFoundException
	 */
	// TODO: add unit-tests
	public Comic getNextComic(String title) throws ComicNotFoundException {
		int i = getComicIndexFromTitle(title);
		i = (i >= (mClasses.length - 1))? 0 : (i + 1);
		return getComicFromIndex(i);
	}

	/**
	 * Get the comic next to the one wrt the input title
	 * @param title comic english name
	 * @return comic object
	 * @throws ComicNotFoundException
	 */
	// TODO: add unit-tests
	public Comic getNextMyComic(String title) throws ComicNotFoundException {
		int f = getComicIndexFromTitle(title);
		int j = mClasses.length;
		for(int i=f+1;i<j;++i) {
			if(mClasses[i].mSel) {
				return getComicFromIndex(i);
			}
		}
		for(int i=0;i<f;++i) {
			if(mClasses[i].mSel) {
				return getComicFromIndex(i);
			}
		}
		return getComicFromIndex(f);
	}

	/**
	 * Get the comic previous to the one wrt the input title
	 * @param title comic english name
	 * @return comic object
	 * @throws ComicNotFoundException
	 */
	// TODO: add unit-tests
	public Comic getPreviousComic(String title) throws ComicNotFoundException {
		int i = getComicIndexFromTitle(title);
		i = (i <= 0)? (mClasses.length - 1) : (i - 1);
		return getComicFromIndex(i);
	}

	/**
	 * Get the comic previous to the one wrt the input title
	 * @param title comic english name
	 * @return comic object
	 * @throws ComicNotFoundException
	 */
	// TODO: add unit-tests
	public Comic getPreviousMyComic(String title) throws ComicNotFoundException {
		int f = getComicIndexFromTitle(title);
		for(int i=f-1;i>=0;--i) {
			if(mClasses[i].mSel) {
				return getComicFromIndex(i);
			}
		}
		for(int i=mClasses.length-1;i>f;--i) {
			if(mClasses[i].mSel) {
				return getComicFromIndex(i);
			}
		}
		return getComicFromIndex(f);
	}

	/**
	 * Return the index of the comic in the list from its title 
	 * @param title comic english name
	 * @return index if found, else -1
	 */
	public int getComicIndexFromTitle(String title) {
		if(!mIdxs.containsKey(title)) {
			return -1;
		}
		return mIdxs.get(title);
	}

	/**
	 * Returns the list of selected comic indices
	 * @return list
	 */
	public int[] getSelectedComicList() {
		int num = numSelected();
		if(num <= 0) {
			return null;
		}
		int[] sel = new int[num];
		int i = 0;
		int j = 0;
		for(ComicClass clz : mClasses) {
			if(clz.mSel) {
				sel[j] = i;
				++j;
			}
			++i;
		}
		return sel;
	}

	/**
	 * Set the comic to be selected
	 * @param title comic english name
	 * @param value true if it is to be selected
	 * @throws ComicNotFoundException
	 */
	public void setSelected(String title, boolean value) throws ComicNotFoundException {
		int idx = getComicIndexFromTitle(title);
		if(idx >= 0) {
			mClasses[idx].mSel = value;
		}
	}

	/**
	 * Tells whether the comic in the current index is selected or not
	 * @param idx index
	 * @return true if it is selected
	 * @throws ComicNotFoundException 
	 */
	public boolean isSelected(int idx) throws ComicNotFoundException {
		if((idx < 0) || (idx >= mClasses.length)) {
			ComicNotFoundException cnf = new ComicNotFoundException("Bad Index passed for 'isSelected'! idx="+idx+"len="+mClasses.length);
			throw cnf;
		}
		return mClasses[idx].mSel;
	}

	/**
	 * Toggle the select bit for the given comic
	 * @param idx comic index
	 * @throws ComicNotFoundException
	 */
	public void toggleSelected(int idx) throws ComicNotFoundException {
		if((idx < 0) || (idx >= mClasses.length)) {
			ComicNotFoundException cnf = new ComicNotFoundException("Bad Index passed for 'toggleSelected'! idx="+idx+"len="+mClasses.length);
			throw cnf;
		}
		mClasses[idx].mSel = (mClasses[idx].mSel)? false : true;
	}

	/**
	 * Set all the comics to be selected
	 * @param value true if it is to be selected
	 */
	public void setAllSelected(boolean value) {
		for(ComicClass clz : mClasses) {
			clz.mSel = value;
		}
	}

	/**
	 * Number of comics selected so far
	 * @return number
	 */
	public int numSelected() {
		int i = 0;
		for(ComicClass clz : mClasses) {
			if(clz.mSel) {
				++i;
			}
		}
		return i;
	}

	/**
	 * Store the selected comics into the json file
	 * @throws IOException 
	 * @throws ComicSDCardFull 
	 */
	public void storeSelected() throws IOException, ComicSDCardFull {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		{
			sb.append("\"selected\" : [ ");
			int i = 0;
			for(ComicClass clz : mClasses) {
				if(clz.mSel) {
					if(i == 0) {
						sb.append("\"" + clz.mName + "\"");
					}
					else {
						sb.append(", \"" + clz.mName + "\"");
					}
					++i;
				}
			}
			sb.append(" ]\n");
		}
		sb.append("}\n");
		FileUtils.storeString(sb.toString(), mSel);
		Log.d(TAG, "Stored the selected comics to file '" + mSel.getPath() + "'");
	}

	/**
	 * File which stores the list of selected comics
	 * @return file
	 */
	public File selectedFile() {
		return mSel;
	}


	/**
	 * Initializer
	 * @param mgr asset manager
	 * @param path path to json file wrt assets
	 * @param selected file containing the list of selected comics
	 * @throws JSONException 
	 * @throws IOException 
	 * @throws ComicNotFoundException 
	 */
	private void _init_(AssetManager mgr, String path, File selected) throws IOException, JSONException, ComicNotFoundException {
		// selected
		JSONArray selArr = null;
		// by somehow if the selected.json was empty, then this can cause parse error
		if(selected.exists() && (selected.length() <= 0)) {
			selected.delete();
		}
		if(selected.exists()) {
			String sel = FileUtils.slurp(new FileInputStream(selected));
			JSONObject selRoot = new JSONObject(sel);
			selArr = selRoot.getJSONArray("selected");
		}
		// parse the file
		String data = FileUtils.slurp(path, mgr);
		JSONObject root = new JSONObject(data);
		JSONArray classes = root.getJSONArray("classes");
		int numClasses = classes.length();
		ArrayList<ComicClass> com_arr = new ArrayList<ComicClass>();
		// set fields
		mPkg = root.getString("package");
		mIdxs = new HashMap<String, Integer>();
		mSel = selected;
		int j = 0;
		for(int i=0;i<numClasses;i++) {
			JSONObject clz = classes.getJSONObject(i);
			String key = clz.getString("name");
			if(!_canAdd(key)) {
				Log.d(TAG, "Not adding the comic '" + key + "' to the list...");
				continue;
			}
			ComicClass cl = new ComicClass();
			cl.mClass = clz.getString("class");
			cl.mName  = key;
			cl.mPref  = clz.getString("pref");
			if(clz.has("new") && clz.getString("new").equals("1")) {
				cl.mNew = true;
			}
			else {
				cl.mNew = false;
			}
			cl.mSel = false;
			com_arr.add(cl);
			mIdxs.put(key, j);
			j++;
		}
		mClasses = new ComicClass[com_arr.size()];
		com_arr.toArray(mClasses);
		if(selArr != null) {
			int num = selArr.length();
			for(int i=0;i<num;++i) {
				String s = selArr.getString(i);
				setSelected(s, true);
			}
		}
	}

	/**
	 * Function to tell whether this comic can be added to comic-class-list.
	 * @param name comic name
	 * @return true if it can be added, else false
	 */
	private boolean _canAdd(String name) {
		if(!FILTER_COMICS) {
			return true;
		}
		for(String dont : FILTER_COMICS_LIST) {
			if(name.compareTo(dont) == 0) {
				return false;
			}
		}
		return true;
	}


	////// COMPARATOR CLASSES FOR SORTING THE COMICS //////
	/** For sorting classes based on ASCII comparison */
	private class AsciiComparator implements Comparator<ComicClass> {
		@Override
		public int compare(ComicClass a, ComicClass b) {
			String sa = a.mName;
			String sb = b.mName;
			return sa.compareTo(sb);
		}
	}

	/** For sorting editorial comics to last */
	private class EditorialLastComparator implements Comparator<ComicClass> {
		@Override
		public int compare(ComicClass a, ComicClass b) {
			String sa = a.mName;
			String sb = b.mName;
			boolean ea = _isEditorial(sa);
			boolean eb = _isEditorial(sb);
			if(ea && !eb) {
				return 1;
			}
			if(!ea && eb) {
				return -1;
			}
			return sa.compareTo(sb);
		}
		private boolean _isEditorial(String a) {
			return (a.indexOf("Editorial : ") == 0);
		}
	}

	/** For sorting classes based on newest first */
	private class NewestFirstComparator implements Comparator<ComicClass> {
		@Override
		public int compare(ComicClass a, ComicClass b) {
			if(a.mNew && !b.mNew) {
				return -1;
			}
			if(!a.mNew && b.mNew) {
				return 1;
			}
			String sa = a.mName;
			String sb = b.mName;
			return sa.compareTo(sb);
		}
	}

	/** For sorting classes based on selected first */
	private class SelectedFirstComparator implements Comparator<ComicClass> {
		@Override
		public int compare(ComicClass a, ComicClass b) {
			if(a.mSel && !b.mSel) {
				return -1;
			}
			if(!a.mSel && b.mSel) {
				return 1;
			}
			String sa = a.mName;
			String sb = b.mName;
			return sa.compareTo(sb);
		}
	}

	/** For sorting classes based on ignore articles */
	private class IgnoreArticlesComparator implements Comparator<ComicClass> {
		@Override
		public int compare(ComicClass a, ComicClass b) {
			String sa = _ignoreArticle(a.mName);
			String sb = _ignoreArticle(b.mName);
			return sa.compareTo(sb);
		}
		private String _ignoreArticle(String a) {
			if(a.indexOf("A ") == 0) {
				return a.substring(2);
			}
			if(a.indexOf("An ") == 0) {
				return a.substring(3);
			}
			if(a.indexOf("The ") == 0) {
				return a.substring(4);
			}
			return a;
		}
	}
	////// COMPARATOR CLASSES FOR SORTING THE COMICS //////

}
