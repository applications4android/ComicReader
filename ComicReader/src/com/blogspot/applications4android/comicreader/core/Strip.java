package com.blogspot.applications4android.comicreader.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.blogspot.applications4android.comicreader.exceptions.BitMapException;
import com.blogspot.applications4android.comicreader.exceptions.ComicSDCardFull;


/**
 * Data-structure for holding info on a comic-strip
 */
public final class Strip {
	/** for logging purposes only */
	private static final String TAG = "Strip";
	/** Limit to the amount of memory to be used by the bitmap (in B) */
	public static final int BITMAP_MEM_LIMIT = 3 * 1024 * 1024;

	/** HTML url for this strip (used to uniquely identify this strip) */
	private String mHtmlUrl;
	/** Strip url (used for sharing the strip) */
	private String mStripUrl;
	/** image stored in sd-card */
	private String mImgFile;
	/** this strip has been read before? */
	private boolean mRead;
	/** favorite strip? */
	private boolean mFav;
	/** uid for the previous strip */
	private String mPrevUid;
	/** uid for the next strip */
	private String mNextUid;
	/** strip title */
	private String mTitle;
	/** strip text */
	private String mText;


	/**
	 * Default constructor
	 * @param html html url
	 * @param imgR image file root
	 */
	public Strip(String html, String imgR) {
		_init_(html, imgR, false, false, null, null, null, null);
	}

	/**
	 * Constructor with initialization
	 * @param html html url
	 * @param imgR image file root
	 * @param read strip read before?
	 * @param fav favorite strip?
	 * @param prev previous comic strip
	 * @param next next comic strip
	 * @param title strip title
	 * @param text strip text
	 */
	public Strip(String html, String imgR, boolean read, boolean fav,
				 String prev, String next, String title, String text) {
		_init_(html, imgR, read, fav, prev, next, title, text);
	}

	/**
	 * Helper function to initialize the contents of the strip
	 * @param obj json object
	 * @return strip object
	 * @throws JSONException 
	 */
	public static Strip readFromJsonObject(JSONObject obj) throws JSONException {
		Strip s = new Strip(null, null);
		s.mHtmlUrl = obj.getString("mHtmlUrl");
		if(obj.has("mStripUrl")) {
			s.mStripUrl = obj.getString("mStripUrl");
		}
		s.mImgFile = obj.getString("mImgFile");
		s.mRead = obj.getBoolean("mRead");
		s.mFav = obj.getBoolean("mFav");
		if(obj.has("mPrevUid")) {
			s.mPrevUid = obj.getString("mPrevUid");
		}
		if(obj.has("mNextUid")) {
			s.mNextUid = obj.getString("mNextUid");
		}
		if(obj.has("mTitle")) {
			s.mTitle = obj.getString("mTitle");
		}
		if(obj.has("mText")) {
			s.mText = obj.getString("mText");
		}
		return s;
	}

	/**
	 * Convert the contents of this strip into json string
	 * @param sb string-builder where to append the contents
	 */
	public void toJsonString(StringBuilder sb) {
		sb.append("{\"mHtmlUrl\":\"" + mHtmlUrl + "\"");
		if(mStripUrl != null) {
			sb.append(", \"mStripUrl\":\"" + mStripUrl + "\"");
		}
		sb.append(", \"mImgFile\":\"" + mImgFile + "\"");
		if(mRead) {
			sb.append(", \"mRead\":\"true\"");
		}
		else {
			sb.append(", \"mRead\":\"false\"");
		}
		if(mFav) {
			sb.append(", \"mFav\":\"true\"");
		}
		else {
			sb.append(", \"mFav\":\"false\"");
		}
		if(mPrevUid != null) {
			sb.append(", \"mPrevUid\":\"" + mPrevUid + "\"");
		}
		if(mNextUid != null) {
			sb.append(", \"mNextUid\":\"" + mNextUid + "\"");
		}
		if(mTitle != null) {
            String temp = mTitle.replaceAll("\"", "\\\"");
			sb.append(", \"mTitle\":\"" + temp + "\"");
		}
		if(mText != null) {
		    String temp = mText.replaceAll("\"", "\\\"");
			sb.append(", \"mText\":\"" + temp + "\"");
		}
		sb.append("}");
	}

	/**
	 * This gets the bitmap from the file (to be used only for comics which can have large strips)
	 * You should have called 'getImage' before calling this function!
	 * @return bitmap object
	 * @throws FileNotFoundException 
	 * @throws BitMapException 
	 */
	public Bitmap getBitmapFromFile() throws FileNotFoundException, BitMapException {
        //Decode image size and fit in exactly the amount of the memory needed.
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        FlushedInputStream fis = new FlushedInputStream(new FileInputStream(mImgFile));
        BitmapFactory.decodeStream(fis, null, o);
        int bytes = o.outWidth * o.outHeight * 3;
        int factor = bytes / BITMAP_MEM_LIMIT;
        if(factor <= 1) {
        	factor = 1;
        }
        else if(factor <= 4) {
        	factor = 2;
        }
        else {
        	factor = 4;
        }
    	BitmapFactory.Options o1 = new BitmapFactory.Options();
    	o1.inSampleSize = factor;
    	Bitmap bm = BitmapFactory.decodeFile(mImgFile, o1);
    	if(bm == null) {
    		String msg = "Failed to decode the image="+mImgFile+" URL="+mHtmlUrl;
    		BitMapException ce = new BitMapException(msg);
    		throw ce;
    	}
    	return bm;
	}

	/**
	 * Fetches the image of the strip
	 * @param p parser which generates strip info
	 * @return path to the image
	 * @throws IOException 
	 * @throws URISyntaxException 
	 * @throws ClientProtocolException 
	 * @throws ComicSDCardFull 
	 */
	public String getImage(ComicParser p) throws ClientProtocolException, URISyntaxException, IOException, ComicSDCardFull {
		downloadImage(p);
		setAsRead(true);
		return mImgFile;
	}

	/**
	 * Downloads the image of the strip if it doesn't exist already
	 * @param p parser which generates strip info
	 * @return true if the image had to be downloaded, else false
	 * @throws IOException 
	 * @throws URISyntaxException 
	 * @throws ClientProtocolException 
	 * @throws ComicSDCardFull 
	 */
	public boolean downloadImage(ComicParser p) throws ClientProtocolException, URISyntaxException, IOException, ComicSDCardFull {
		File img = new File(mImgFile);
		if(img.exists()) {
			return false;
		}
		String surl = p.parse(mHtmlUrl, this);
		Log.d(TAG, "Image url="+surl);
		mStripUrl = surl;
		Downloader.dnldImage(mImgFile, new URI(surl));
		return true;
	}

	/**
	 * Creates a valid file name out of the strip's title
	 * @return valid file name
	 */
	public String currentTitleAsValidFilename() {
		if(mTitle == null) {
			return null;
		}
		return mTitle.replaceAll("[^a-zA-Z0-9]", "_");
	}

	/**
	 * Mark this strip as read/un-read
	 * @param read read/un-read
	 */
	public void setAsRead(boolean read) {
		mRead = read;
	}

	/**
	 * Set this comic as favorite
	 * @param fav favorite or not
	 */
	public void setAsFavorite(boolean fav) {
		mFav = fav;
	}

	/**
	 * Set the previous comic strip
	 * @param uid previous strip
	 */
	public void setPrevious(String uid) {
		mPrevUid = uid;
	}

	/**
	 * Set the next comic strip
	 * @param uid next strip
	 */
	public void setNext(String uid) {
		mNextUid = uid;
	}

	/**
	 * Set the strip text
	 * @param txt text
	 */
	public void setText(String txt) {
		mText = txt;
	}

	/**
	 * Set the strip title
	 * @param title title
	 */
	public void setTitle(String title) {
		mTitle = title;
	}

	/**
	 * Returns the UID for this strip (nothing but the html-url)
	 * @return uid
	 */
	public String uid() {
		return mHtmlUrl;
	}

	/**
	 * Returns the strip url
	 * @return url
	 */
	public String getStripUrl() {
		return mStripUrl;
	}

	/**
	 * Returns the path to the stored image file
	 * @return image file path
	 */
	public String getImagePath() {
		return mImgFile;
	}

	/**
	 * Whether this strip has been read
	 * @return true if it has been read
	 */
	public boolean isRead() {
		return mRead;
	}

	/**
	 * Whether this strip is favorite
	 * @return true if is favorite
	 */
	public boolean isFavorite() {
		return mFav;
	}

	/**
	 * Get the previous comic strip
	 * @return previous strip
	 */
	public String getPrevious() {
		return mPrevUid;
	}

	/**
	 * Is there a previous strip for this strip
	 * @return true if it has
	 */
	public boolean hasPrevious() {
		return (mPrevUid != null);
	}

	/**
	 * Get the next comic strip
	 * @return next strip
	 */
	public String getNext() {
		return mNextUid;
	}

	/**
	 * Is there a next strip for this strip
	 * @return true if it has
	 */
	public boolean hasNext() {
		return (mNextUid != null);
	}

	/**
	 * Get the strip title
	 * @return title
	 */
	public String getTitle() {
		return Downloader.decodeHtml(mTitle);
	}

	/**
	 * Get the strip text
	 * @return text
	 */
	public String getText() {
		return Downloader.decodeHtml(mText);
	}

	/**
	 * Checks whether the strip has hover-text or not
	 * @return true if it has
	 */
	public boolean hasText() {
    	if(mText == null) {
    		return false;
    	}
    	if(mText.equals("") || mText.equals("-NA-")) {
    		return false;
    	}
    	return true;
	}

	/**
	 * Initialize the member variables
	 * @param html html url
	 * @param imgR image file root
	 * @param read strip read before?
	 * @param fav favorite strip?
	 * @param prev previous comic strip
	 * @param next next comic strip
	 * @param title strip title
	 * @param text strip text
	 */
	private void _init_(String html, String imgR, boolean read, boolean fav,
						String prev, String next, String title, String text) {
		mHtmlUrl = html;
		mStripUrl = null;
		_setImgFile_(imgR);
		mRead = read;
		mFav = fav;
		mPrevUid = prev;
		mNextUid = next;
		mTitle = title;
		mText = text;
	}

	/**
	 * Sets the image file path
	 * @param imgR image root folder
	 */
	private void _setImgFile_(String imgR) {
		if(imgR != null) {
			mImgFile = imgR + "/" + Md5Hash.str2md5(mHtmlUrl) + ".img";
		}
	}
}
