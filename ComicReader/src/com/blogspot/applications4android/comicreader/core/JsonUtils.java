package com.blogspot.applications4android.comicreader.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.res.AssetManager;


/**
 * Has some handy utility functions viz. for operating on json objects
 */
public class JsonUtils {

	/**
	 * Private constructor!
	 */
	private JsonUtils() {
	}

	/**
	 * Returns the root JSON object found in the file (stored in android_asset)
	 * @param file the file of interest
	 * @param am the asset manager which will be used to find the input file
	 * @return the JSON object of interest.
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public static JSONObject settingsRoot(String file, AssetManager am) throws IOException, JSONException {
		return jsonRoot(am.open(file));
	}

	/**
	 * Returns root JSON object found in the file
	 * @param is input stream from where to read the json-string
	 * @return json-root object
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public static JSONObject jsonRoot(InputStream is) throws IOException, JSONException {
		String data = FileUtils.slurp(is);
		return new JSONObject(data);
	}

	/**
	 * Returns a list of comics from the JSON object found in the file (stored in android_asset)
	 * @param file the file of interest
	 * @param am the asset manager which will be used to find the input file
	 * @param key the key which stores the list of all classes
	 * @return the JSON array of interest
	 * @throws JSONException 
	 * @throws IOException 
	 */
	public static JSONArray listOfComics(String file, AssetManager am, String key) throws IOException, JSONException {
		JSONObject root = settingsRoot(file, am);
		JSONArray classes = root.getJSONArray(key);
		return classes;
	}

	/**
	 * Given the json-array of classes, this will give the list of all comic class names
	 * @param classes the json-array.
	 * @return string-array of the class names of the comics
	 * @throws JSONException 
	 */
	public static String[] listOfAllComicClasses(JSONArray classes) throws JSONException {
		int num = classes.length();
		String[] items = new String[num];
		for(int i=0;i<num;i++) {
			items[i] = classes.getJSONObject(i).getString("class");
		}
		return items;
	}

	/**
	 * Returns a list of all comic class names from the JSON array found in the file (stored in android_asset)
	 * @param file the file of interest
	 * @param am the asset manager which will be used to find the input file
	 * @param key the key which stores the list of all classes
	 * @return string-array of the class names of the comics
	 * @throws JSONException 
	 * @throws IOException 
	 */
	public static String[] listOfAllComicClasses(String file, AssetManager am, String key) throws IOException, JSONException {
		JSONArray classes = listOfComics(file, am, "classes");
		return listOfAllComicClasses(classes);
	}

	/**
	 * Given the json-array of classes, this will give the list of all comic names
	 * @param classes the json-array.
	 * @return string-array of the names of the comics
	 * @throws JSONException 
	 */
	public static String[] listOfAllComicNames(JSONArray classes) throws JSONException {
		int num = classes.length();
		String[] items = new String[num];
		for(int i=0;i<num;i++) {
			items[i] = classes.getJSONObject(i).getString("name");
		}
		return items;
	}

	/**
	 * Returns a list of all comic names from the JSON array found in the file (stored in android_asset)
	 * @param file the file of interest
	 * @param am the asset manager which will be used to find the input file
	 * @param key the key which stores the list of all classes
	 * @return string-array of the names of the comics
	 * @throws JSONException 
	 * @throws IOException 
	 */
	public static String[] listOfAllComicNames(String file, AssetManager am, String key) throws IOException, JSONException {
		JSONArray classes = listOfComics(file, am, "classes");
		return listOfAllComicNames(classes);
	}

	/**
	 * Given the json-array of classes, this will give the list of all new comic indices
	 * @param classes the json-array.
	 * @return int-array of the indices of the new comics
	 * @throws JSONException 
	 */
	public static ArrayList<Integer> listOfAllNewComicIndices(JSONArray classes) throws JSONException {
		int num = classes.length();
		ArrayList<Integer> items = new ArrayList<Integer>();
		for(int i=0;i<num;i++) {
			JSONObject ob = classes.getJSONObject(i);
			if(ob.has("new")) {
				items.add(i);
			}
		}
		return items;
	}

	/**
	 * Returns a list of all new comic indices from the JSON array found in the file (stored in android_asset)
	 * @param file the file of interest
	 * @param am the asset manager which will be used to find the input file
	 * @param key the key which stores the list of all classes
	 * @return int-array of the indices of the new comics
	 * @throws JSONException 
	 * @throws IOException 
	 */
	public static ArrayList<Integer> listOfAllNewComicIndices(String file, AssetManager am, String key) throws IOException, JSONException {
		JSONArray classes = listOfComics(file, am, "classes");
		return listOfAllNewComicIndices(classes);
	}

	/**
	 * Given the json-array of classes, this will give the list of all comic pref keys
	 * @param classes the json-array.
	 * @return string-array of the pref-keys of the comics
	 * @throws JSONException 
	 */
	public static String[] listOfAllComicPrefKeys(JSONArray classes) throws JSONException {
		int num = classes.length();
		String[] items = new String[num];
		for(int i=0;i<num;i++) {
			items[i] = classes.getJSONObject(i).getString("pref");
		}
		return items;
	}

	/**
	 * Returns a list of all new comic indices from the JSON array found in the file (stored in android_asset)
	 * @param file the file of interest
	 * @param am the asset manager which will be used to find the input file
	 * @param key the key which stores the list of all classes
	 * @return string-array of the pref-keys of the comics
	 * @throws JSONException 
	 * @throws IOException 
	 */
	public static String[] listOfAllComicPrefKeys(String file, AssetManager am, String key) throws IOException, JSONException {
		JSONArray classes = listOfComics(file, am, "classes");
		return listOfAllComicPrefKeys(classes);
	}
}
