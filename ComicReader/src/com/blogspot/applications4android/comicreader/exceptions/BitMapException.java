package com.blogspot.applications4android.comicreader.exceptions;


/**
 * To handle all errors from this app!
 */
public class BitMapException extends Exception {
	/** unique version ID? */
	private static final long serialVersionUID = -2508899858258046290L;


	public BitMapException() {
		super();
	}

	public BitMapException(String detailMessage) {
		super(detailMessage);
	}

	public BitMapException(Throwable throwable) {
		super(throwable);
	}

	public BitMapException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
