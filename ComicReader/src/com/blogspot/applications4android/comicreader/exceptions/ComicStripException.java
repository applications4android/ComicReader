package com.blogspot.applications4android.comicreader.exceptions;


/**
 * To handle all errors from this app!
 */
public class ComicStripException extends Exception {
	/** unique version ID? */
	private static final long serialVersionUID = -2508899858258046289L;


	public ComicStripException() {
		super();
	}

	public ComicStripException(String detailMessage) {
		super(detailMessage);
	}

	public ComicStripException(Throwable throwable) {
		super(throwable);
	}

	public ComicStripException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
