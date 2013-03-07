package com.blogspot.applications4android.comicreader.core;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.json.JSONException;

import android.content.res.AssetManager;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Time;
import android.util.Log;

import com.blogspot.applications4android.comicreader.exceptions.ComicNotFoundException;
import com.blogspot.applications4android.comicreader.exceptions.ComicSDCardFull;

/**
 * Class containing helper methods for working on files
 */
public class FileUtils {
	/** buffer size (in bytes) for BufferedReader's */
	public static final int BUFF_SIZE = 8192;
	/** string builder size (in bytes) */
	public static final int SB_CAPACITY = 512;
	/** minimum sd-card free space required for this app (in MB) */
	public static final int MIN_BYTES_SDCARD_MB = 5;
	/** root folder for storing info */
	public static final String COMIC_ROOT = "ComicReader";


	/**
	 * Private constructor!
	 */
	private FileUtils() {
	}

	/**
	 * Recursively delete a directory (equivalent to 'rm -rf')
	 * @param dir directory to be deleted
	 */
	// TODO: add unit-tests
	public static void recursiveDelete(File dir) {
		if(!dir.exists()) {
			return;
		}
		Log.d("TAG", "deleting "+dir.getPath());
		if(dir.isDirectory()) {
			for(File child : dir.listFiles()) {
				if(child.isDirectory()) {
					recursiveDelete(child);
				}
				else {
					child.delete();
				}
			}
		}
		dir.delete();
	}

	/**
	 * Creates an empty file
	 * @param file file to be created
	 * @return true if the file is created, else false
	 */
	public static boolean touch(String file) {
		File f = new File(file);
		if(f.exists()) {
			return true;
		}
		try {
			FileOutputStream fos = new FileOutputStream(f);
			fos.close();
			return true;
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		catch(IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Calculate the number of lines in the plain text file
	 * @param in reader
	 * @return num-lines
	 */
	public static int numLines(Reader in) {
		try {
			BufferedReader br = new BufferedReader(in, BUFF_SIZE);
			int num = 0;
			while(br.readLine() != null) {
				num++;
			}
			br.close();
			return num;
		}
		catch(Exception e) {
			return 0;
		}
	}

	/**
	 * Calculate the number of lines in the plain text file.
	 * @param file the file of interest
	 * @return number of lines in the file
	 */
	public static int numLines(File file) {
		try {
			return numLines(new FileReader(file));
		}
		catch(Exception e) {
			return 0;
		}
	}

	/**
	 * Read the whole file into a string. This will close the stream after reading!
	 * @param is file pointer
	 * @return string
	 * @throws IOException
	 */
	public static String slurp(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder(SB_CAPACITY);
		BufferedReader br = new BufferedReader(new InputStreamReader(is), BUFF_SIZE);
		String temp;
		while((temp = br.readLine()) != null) {
			sb.append(temp);
		}
		br.close();
		return sb.toString();
	}	

	/**
	 * Slurp a file (which is present in the android_asset) into a string
	 * @param file the file of interest
	 * @param am the asset manager which will be used to find the input file
	 * @return its contents as a string
	 * @throws IOException 
	 */
	public static String slurp(String file, AssetManager am) throws IOException {
		return slurp(am.open(file));
	}

	/**
	 * Store a string to the file
	 * @param s string
	 * @param f file
	 * @throws IOException 
	 * @throws ComicSDCardFull
	 */
	public static void storeString(String s, File f) throws IOException, ComicSDCardFull {
		checkFreeSpaceSdcard();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
		bw.write(s);
		bw.close();
	}

	/**
	 * Delete a file/folder if it exists
	 * @param file the file to be deleted.
	 * @return true if deletion was successful. Else false.
	 */
	// unit-test for this is inside DownloaderTest.java!
	public static boolean delete(File file) {
		if(file.exists()) {
			return file.delete();
		}
		return true;
	}

	/**
	 * Create a folder if it doesn't exist already.
	 * @param folder the folder to be created if it doesn't exist.
	 * @return true if the folder was created successfully
	 */
	public static boolean mkdir(File folder) {
		if(!folder.exists()) {
			return folder.mkdirs();
		}
		return true;
	}

	/**
	 * Get a temporary filename using Time + Random.
	 * @param prefix the prefix to be used for this temp file.
	 * @param suffix the suffix to be used for this temp file.
	 * @return the name of the temp file.
	 */
	public static String getTempFile(String prefix, String suffix) {
		Time now = new Time();
		now.setToNow();
		Random rnd = new Random(now.toMillis(false));
		Long val = now.toMillis(false) + rnd.nextLong();
		String filename = Long.toHexString(val);
		filename = prefix + filename + suffix;
		return filename;
	}

	/**
	 * Get a temporary filename using Time + Random. The prefix and
	 * suffix for this temp file will be "" and ".temp" respectively.
	 * @return the name of the temp file.
	 */
	public static String getTempFile() {
		return getTempFile("", ".temp");
	}

	/**
	 * Checks whether there's enough space on the sd-card
	 * @throws ComicSDCardFull
	 */
	public static void checkFreeSpaceSdcard() throws ComicSDCardFull {
		long av = sdcardAvailableBytesMB();
		if(av < MIN_BYTES_SDCARD_MB) {
			throw new ComicSDCardFull("ComicReader requires atleast "+MIN_BYTES_SDCARD_MB
					+"MB of free space on sd-card. You have only "+av+"MB free!");
		}
	}

	/**
	 * Gets the available number of bytes in your sdcard (in MB)
	 * @return available number of bytes (returns 0 if sdcard is unavailable)
	 */
	public static long sdcardAvailableBytesMB() {
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return 0;
		}
		StatFs fs = new StatFs(Environment.getExternalStorageDirectory().getPath());
		long avail = ((long)fs.getBlockSize()) * ((long)fs.getAvailableBlocks());
		avail /= (1024 * 1024);
		return avail;
	}

	/**
	 * Returns the path to the sdcard
	 * @return path. Returns null if sdcard is not mounted
	 */
	public static File getSdcard() {
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return null;
		}
		return Environment.getExternalStorageDirectory();
	}

	/**
	 * Gets the root comic folder
	 * @return folder
	 */
	public static File getComicRoot() {
		File root = new File(getSdcard(), COMIC_ROOT);
		root.mkdirs();
		return root;
	}

	/**
	 * Copy source file to destination
	 * @param src source location
	 * @param dst destination location
	 * @return true on success
	 */
	public static boolean copyFile(File src, File dst) {
		if(dst.exists()) {
			return true;
		}
		try {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dst);
			// Transfer bytes from in to out
			byte[] buf = new byte[BUFF_SIZE];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * saves the list to a zip file to the sdcard
	 * 
	 * @param mList the list of comics.
	 * @param listLocation Location of where the selected.json are
	 * @param zipName the name of the zip file (path as well)
	 * @return
	 */
	public static boolean favSave(ComicClassList mList, String listLocation, String zipName) {
		ArrayList<String> files = new ArrayList<String>();
		files.add(listLocation);
		for (int cI : mList.getSelectedComicList()) {
			try {
				Comic c = mList.getComicFromIndex(cI);
				c.readProperties();
				if (c.containsFavs()) {
					String fileName = getComicRoot() + "/props/" + c.getName() + ".json";
					files.add(fileName);
				}
			} catch (ComicNotFoundException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		zip(files, zipName);
		return true;
	}

	/**
	 * Loads favorite list from zip to ComicReader folders
	 * 
	 * @return
	 */
	public static boolean favLoad(String _zipFile) {
		boolean successful = false;

		_dirChecker(getComicRoot() + "/props/");

		successful = unzip(_zipFile);

		return successful;
	}

	/**
	 * a method to help zip up fav files
	 * 
	 * @param files
	 *            which will hold all the files (path as well) that will be
	 *            zipped up
	 * @param zipFile
	 *            the name of the zip file (path as well)
	 */
	private static void zip(ArrayList<String> files, String zipFile) {
		try {
			BufferedInputStream origin = null;
			FileOutputStream dest = new FileOutputStream(zipFile);

			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

			byte data[] = new byte[BUFF_SIZE];

			for (int i = 0; i < files.size(); i++) {
				Log.v("Compress", "Adding: " + files.get(i));
				FileInputStream fi = new FileInputStream(files.get(i));
				origin = new BufferedInputStream(fi, BUFF_SIZE);
				ZipEntry entry = new ZipEntry(files.get(i).substring(files.get(i).lastIndexOf("/") + 1));
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFF_SIZE)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}

			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * a method to unzip up fav files
	 * 
	 * @param files
	 *            which will hold all the files (path as well) that will be
	 *            zipped up
	 * @param zipFile
	 *            the name of the zip file (path as well)
	 * @return true if successful
	 */
	public static boolean unzip(String _zipFile) {
		try {
			FileInputStream fin = new FileInputStream(_zipFile);
			ZipInputStream zin = new ZipInputStream(fin);
			ZipEntry ze = null;
			while ((ze = zin.getNextEntry()) != null) {
				Log.v("Decompress", "Unzipping " + ze.getName());
				String unzipLocation= ze.getName().equals("selected.json") ? getComicRoot() +"/"+ ze.getName() : getComicRoot() + "/props/"+ ze.getName();

				FileOutputStream fout = new FileOutputStream(unzipLocation);
				for (int c = zin.read(); c != -1; c = zin.read()) {
					fout.write(c);

				}
				zin.closeEntry();
				fout.close();

			}
			zin.close();
		} catch (Exception e) {
			Log.e("Decompress", "unzip", e);
			return false;
		}
		return true;
	}

	/**
	 * adds directory if needed to comic reader.
	 * 
	 * @param dir
	 */
	private static void _dirChecker(String dir) {
		File f = new File(dir);

		if (!f.isDirectory()) {
			f.mkdirs();
		}
	}

}
