package com.blogspot.applications4android.comicreader.exceptions;


/**
 * Exception thrown when Sd-card is full
 */
public class ComicSDCardFull extends Exception {
	private static final long serialVersionUID = -2470030817897777657L;


	public ComicSDCardFull() {
		super();
	}

	public ComicSDCardFull(String detailMessage) {
		super(detailMessage);
	}

	public ComicSDCardFull(Throwable throwable) {
		super(throwable);
	}

	public ComicSDCardFull(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
