package com.blogspot.applications4android.comicreader.exceptions;


/**
 * To handle all errors from this app!
 */
public class ComicLatestException extends Exception {
	/** unique version ID? */
	private static final long serialVersionUID = -2508899858258046287L;


	public ComicLatestException() {
		super();
	}

	public ComicLatestException(String detailMessage) {
		super(detailMessage);
	}

	public ComicLatestException(Throwable throwable) {
		super(throwable);
	}

	public ComicLatestException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
