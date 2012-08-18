package com.blogspot.applications4android.comicreader.exceptions;


/**
 * To handle all errors from this app!
 */
public class ComicNotFoundException extends Exception {
	/** unique version ID? */
	private static final long serialVersionUID = -2508899858258046288L;


	public ComicNotFoundException() {
		super();
	}

	public ComicNotFoundException(String detailMessage) {
		super(detailMessage);
	}

	public ComicNotFoundException(Throwable throwable) {
		super(throwable);
	}

	public ComicNotFoundException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
