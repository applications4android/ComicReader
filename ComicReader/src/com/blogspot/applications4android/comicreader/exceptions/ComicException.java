package com.blogspot.applications4android.comicreader.exceptions;


/**
 * To handle all errors from this app!
 */
public class ComicException extends Exception {
	/** unique version ID? */
	private static final long serialVersionUID = -2508899858258046289L;


	public ComicException() {
		super();
	}

	public ComicException(String detailMessage) {
		super(detailMessage);
	}

	public ComicException(Throwable throwable) {
		super(throwable);
	}

	public ComicException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
