package com.blogspot.applications4android.comicreader.core;

import java.math.BigInteger;
import java.security.MessageDigest;


/**
 * Helper class for calculating MD5 hash of the input string.
 */
public class Md5Hash {

	/**
	 * Private constructor!
	 */
	private Md5Hash() {
	}

	/**
	 * Evaluate the MD5 hash of the input string.
	 * @param str string whose MD5 value needs to be evaluated.
	 * @return string containing the MD5 value in hex format.
	 *         If unsuccessful, it returns 'null'.
	 */
	public static String str2md5(String str) {
		if(str == null) {
			return null;
		}
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes());
			byte[] digest = md5.digest();
			BigInteger bi = new BigInteger(1, digest);
			return String.format("%0" + (digest.length << 1) + "X", bi);
		}
		catch(Exception e) {
			return null;
		}
	}
}
