package com.blogspot.applications4android.comicreader.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;


/**
 * Helper class to get app information
 */
// TODO: add unit-tests
public class AppInfo {

	/**
	 * Private constructor!
	 */
	private AppInfo() {
	}

	/**
	 * Helper function to quickly get the app-version
	 * @param ctxt application context
	 * @param cls main class for the application
	 * @return version string
	 */
	public static String getVersion(Context ctxt, Class<?> cls) {
		try {
			String pkg_name = (new ComponentName(ctxt, cls)).getPackageName();
			PackageInfo pi = ctxt.getPackageManager().getPackageInfo(pkg_name, 0);
			return pi.versionName;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
