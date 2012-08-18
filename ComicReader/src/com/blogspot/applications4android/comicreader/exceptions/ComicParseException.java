package com.blogspot.applications4android.comicreader.exceptions;


/**
 * To handle all errors from this app!
 */
public class ComicParseException extends Exception {
	/** unique version ID? */
	private static final long serialVersionUID = -2508899858258046286L;


	public ComicParseException() {
		super();
	}

	public ComicParseException(String detailMessage) {
		super(detailMessage);
	}

	public ComicParseException(Throwable throwable) {
		super(throwable);
	}

	public ComicParseException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
