package com.blogspot.applications4android.comicreader.exceptions;


/**
 * To handle all errors from this app!
 */
public class ComicSortException extends Exception {
	/** unique version ID? */
	private static final long serialVersionUID = -2508899858258046288L;


	public ComicSortException() {
		super();
	}

	public ComicSortException(String detailMessage) {
		super(detailMessage);
	}

	public ComicSortException(Throwable throwable) {
		super(throwable);
	}

	public ComicSortException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
