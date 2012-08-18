package com.blogspot.applications4android.comicreader.core;

import java.util.Random;

/**
 * Utilities class for generating random numbers
 */
public class RandUtils {
	/** one copy of this class! */
	private static RandUtils m_rand = null;
	/** random number generator */
	private Random m_rng;

	/**
	 * Constructor (private!)
	 */
	private RandUtils() {
		m_rng = new Random(System.currentTimeMillis());
	}

	/**
	 * Gets the object of this class
	 * @return the desired object
	 */
	public static RandUtils getObj() {
		if(m_rand != null) {
			return m_rand;
		}
		m_rand = new RandUtils();
		return m_rand;
	}

	/**
	 * Gets the RNG for this class
	 * @return desired object of the class java.util.Random
	 */
	public static Random getRNG() {
		return getObj().m_rng;
	}

	/**
	 * Generate a positive random integer
	 * @return positive integer
	 */
	public static int getPositiveInt() {
		int i = getObj().m_rng.nextInt();
		if(i < 0) {
			i = -i;
		}
		return i;
	}

	/**
	 * Generate a positive random integer
	 * @param limit numbers should be between 0 to limit-1
	 * @return positive integer
	 */
	public static int getPositiveInt(int limit) {
		int i = getObj().m_rng.nextInt();
		if(i < 0) {
			i = -i;
		}
		i %= limit;
		return i;
	}

	/**
	 * Generate a positive random integer
	 * @param limit numbers should be between 0 to limit-1
	 * @param first the first number in the range first to first + limit - 1
	 * @return positive integer
	 */
	public static int getPositiveInt(int limit, int first) {
		int i = getObj().m_rng.nextInt();
		if(i < 0) {
			i = -i;
		}
		i %= limit;
		i += first;
		return i;
	}

	/**
	 * Generate a positive random long integer
	 * @return positive long integer
	 */
	public static long getPositiveLong() {
		long i = getObj().m_rng.nextLong();
		if(i < 0) {
			i = -i;
		}
		return i;
	}

	/**
	 * Generate a positive random long integer
	 * @param limit numbers should be between 0 to limit-1
	 * @return positive long integer
	 */
	public static long getPositiveLong(long limit) {
		long i = getObj().m_rng.nextLong();
		if(i < 0) {
			i = -i;
		}
		i %= limit;
		return i;
	}

	/**
	 * Generate a positive random long integer
	 * @param limit numbers should be between 0 to limit-1
	 * @param first the first number in the range first to first + limit - 1
	 * @return positive long integer
	 */
	public static long getPositiveLong(long limit, long first) {
		long i = getObj().m_rng.nextLong();
		if(i < 0) {
			i = -i;
		}
		i %= limit;
		i += first;
		return i;
	}
}
