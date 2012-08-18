package com.blogspot.applications4android.comicreader.core;


/**
 * Defines a bound containing long-int's
 */
public class Bound {
	/** lower bound */
	public long min;
	/** upper bound */
	public long max;


	/**
	 * Default constructor
	 */
	public Bound() {
		min = 0;
		max = 0;
	}

	/**
	 * Handy constructor
	 * @param _min min value
	 * @param _max max value
	 */
	public Bound(long _min, long _max) {
		min = _min;
		max = _max;
	}

	/**
	 * Checks for conformance to the bound
	 * @param val value to be checked against
	 * @return true if it is inside the bounds.
	 */
	public boolean isUnderLimit(long val) {
		return ((val >= min) && (val <= max));
	}
}
