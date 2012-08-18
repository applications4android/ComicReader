package com.blogspot.applications4android.comicreader.core;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;


/**
 * Cache folder for a comic
 */
public class Cache {
	/** cache folder name */
	public static final String CACHE = "cache";
	/** maximum number of files to be kept in the cache before recycling */
	public static final int MAX_FILES = 60;
	/** minimum number of files to be kept while cleaning the cache */
	public static final int MIN_FILES = 30;

	/** root folder for the cache */
	private File mRoot;


	/**
	 * Constructor
	 * @param name name of the comic this cache is for
	 */
	public Cache(String name) {
		String path = CACHE + "/" + name;
		mRoot = new File(FileUtils.getComicRoot(), path);
		mRoot.mkdirs();
	}

	/**
	 * Constructor (to be generally used in test-cases)
	 * @param root root folder
	 */
	public Cache(File root) {
		mRoot = root;
		mRoot.mkdirs();
	}

	/**
	 * Path to the cache folder
	 * @return path
	 */
	public String cachePath() {
		return mRoot.getPath();
	}

	/**
	 * Delete all the files in the cache
	 */
	public void clearCache() {
		for(File f : mRoot.listFiles()) {
			f.delete();
		}
	}

	/**
	 * Clear only the oldest files to make space for newer ones
	 */
	public void makeSpace() {
		File[] files = mRoot.listFiles();
		if(files.length <= MAX_FILES) {
			return;
		}
		Arrays.sort(files, new LrcComparator());
		int num = files.length - MIN_FILES;
		for(int i=0;i<num;++i) {
			files[i].delete();
		}
	}

	/**
	 * Number of files in the cache
	 * @return number
	 */
	public int numFiles() {
		return mRoot.list().length;
	}


	/** For sorting files based on time created */
	private class LrcComparator implements Comparator<File> {
		@Override
		public int compare(File arg0, File arg1) {
			return (arg0.lastModified() >= arg1.lastModified())? -1 : 1;
		}
	}
}
