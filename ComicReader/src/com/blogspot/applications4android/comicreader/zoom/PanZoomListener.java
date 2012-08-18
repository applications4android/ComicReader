package com.blogspot.applications4android.comicreader.zoom;

import com.blogspot.applications4android.comicreader.ComicStripViewer;
import com.blogspot.applications4android.comicreader.R;
import com.blogspot.applications4android.comicreader.core.Comic;

import android.os.Handler;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


/**
 * Class responsible for listening to all sorts of events for pan/zoom
 */
public class PanZoomListener implements OnTouchListener {
	/** mode = zoom in */
	public static final int MODE_ZOOM_IN = 0x1;
	/** mode = zoom out */
	public static final int MODE_ZOOM_OUT = 0x2;
	/** minimum distance difference for working on zoom */
	public static final float MIN_DIST_ZOOM = 10f;
	/** zomm in/out factor for the button based zoom */
	public static final float ZOOM_FACTOR = 0.15f;

	/** minimum duration for double tap */
	public static final long MIN_DOUBLE_TAP = 25;
	/** maximum duration for double tap */
	public static final long MAX_DOUBLE_TAP = 200;
	/** RESET mode */
	public static final int MODE_RESET = 0x1;
	/** ZOOM mode */
	public static final int MODE_ZOOM = 0x2;
	/** DRAG mode */
	public static final int MODE_PAN = 0x3;
	/** time taken (in ms) for changing visibility */
	public static final long DELAY_VISIBILITY = 3500;
	/** ratio above which is considered to be a right/left swipe */
	public static final float RATIO_SWIPE_HORIZONTAL = 0.3f;
	/** ratio above which is considered to be an up/down swipe */
	public static final float RATIO_SWIPE_VERTICAL = 3f;
	/** minimum velocity needed along X for it to be considered for right/left swipe */
	public static final float MIN_VELX_SWIPE_HORIZONTAL = 250f;
	/** minimum velocity needed along Y for it to be considered for up/down swipe */
	public static final float MIN_VELY_SWIPE_VERTICAL = 250f;
	/** minimum velocity needed for deciding on a pan-fling */
	public static final float MIN_FLING_VEL = 600f;

	/** controller of the view */
	private PanZoomControl mController;
	/** maximum possible fling velocity */
	private final int mMaxFlingVel = ViewConfiguration.getMaximumFlingVelocity();
	/** previous distance between the 2 fingers during pinch-zoom */
	private float mPrevDist = 0;
	/** previously touched x-location */
	private float mPrevX = 0;
	/** previously touched y-location */
	private float mPrevY = 0;
	/** time when previous down was made */
	private long mPrevDown = 0;
	/** what was the previous mode when double tap was made */
	private int mPrevMode = MODE_RESET;

	/** tracker to measure velocity for fling event */
	private VelocityTracker mVt = null;
	/** mode */
	private int mMode = MODE_ZOOM;
	/** comic series activity */
	private ComicStripViewer mComicAct = null;
	/** layout containing the zoom controls */
	private LinearLayout mCtrls;
	/** runnable used to change the visibility */
	private Runnable mChangeVisibility = new Runnable() {
		public void run() {
			mCtrls.setVisibility(View.INVISIBLE);
		}
	};
	/** handler used to launch the runnable */
	private Handler mHandler = new Handler();


	/**
	 * Constructor
	 * @param ctrl the pan/zoom controller
	 * @param ctrls controls layout
	 */
	public PanZoomListener(PanZoomControl ctrl, LinearLayout ctrls) {
		mController = ctrl;
		mCtrls = ctrls;
    	mCtrls.setVisibility(View.INVISIBLE);
	}

	/**
	 * Set the comic activity which needs to be used for displaying previous/next strips
	 * @param ser the comic activity
	 */
	public void setComicActivity(ComicStripViewer ser) {
		mComicAct = ser;
		if(mComicAct.getComic().getDefaultZoom() > PanZoomControl.MIN_ZOOM) {
			mMode = MODE_ZOOM;
		}
		else {
			mMode = MODE_RESET;
		}
		mCtrls = mComicAct.getControlsLayout();
	}

	/**
	 * Set the current mode as zoom. This is used only by the on-screen zoom in/out buttons!
	 */
	public void setModeToZoom() {
		mMode = MODE_ZOOM;
	}

	/**
	 * Set the current mode as pan. This is used only by the on-screen reset button!
	 */
	public void setModeToPan() {
		mMode = MODE_PAN;
	}

	/**
	 * Callback when the first finger has been lifted
	 * @param me motion event
	 * @param v view generating this event
	 * @return whether or not this event was consumed
	 */
	public boolean onFling(MotionEvent me, View v) {
		if(mVt == null) {
			return false;
		}
		mVt.computeCurrentVelocity(1000, mMaxFlingVel);
		if(mMode == MODE_PAN) {
			float velx = Math.abs(mVt.getXVelocity());
			float vely = Math.abs(mVt.getYVelocity());
			if((velx >= MIN_FLING_VEL) || (vely >= MIN_FLING_VEL)) {
				mController.startFling(-mVt.getXVelocity() / v.getWidth(), -mVt.getYVelocity() / v.getHeight());
				return true;
			}
			return false;
		}
		else if(mMode == MODE_RESET) {
			float velx = Math.abs(mVt.getXVelocity());
			float vely = Math.abs(mVt.getYVelocity());
			float ratio = velx / vely;
			float ratioU = 1 / ratio;
			if((ratioU < RATIO_SWIPE_VERTICAL) && (ratio < RATIO_SWIPE_HORIZONTAL)) {
				return true;
			}
			if((velx < MIN_VELX_SWIPE_HORIZONTAL) && (vely < MIN_VELY_SWIPE_VERTICAL)) {
				return true;
			}
			if(ratioU > RATIO_SWIPE_VERTICAL) {
				try {
					if(mVt.getYVelocity() > 0f) {
						mComicAct.setupPreviousComic();
					}
					else {
						mComicAct.setupNextComic();
					}
				}
				catch(Exception e) {
					e.printStackTrace();
					Toast.makeText(mComicAct, "Failed to setup next/previous comic! "+e.getMessage(), Toast.LENGTH_LONG);
				}
			}
			else if(ratio > RATIO_SWIPE_HORIZONTAL) {
				int type = (mVt.getXVelocity() > 0f)? type = Comic.NAV_PREVIOUS : Comic.NAV_NEXT;
				if(mComicAct != null) {
					mComicAct.displayStrip(type);
				}
				hideControls();
				mMode = mPrevMode;
			}
			return true;
		}
		return false;
	}

	/**
	 * Remove the previous callback and set it again.
	 */
	public void resetChangeVisibility() {
		if(mComicAct == null) {
			return;
		}
		mCtrls.setVisibility(View.VISIBLE);
		mHandler.removeCallbacks(mChangeVisibility);
		mHandler.postDelayed(mChangeVisibility, DELAY_VISIBILITY);
		Button text = (Button) mCtrls.findViewById(R.id.image_text);
		if(mComicAct.getComic().currentHasImageText()) {
			text.setVisibility(View.VISIBLE);
		}
		else {
			text.setVisibility(View.INVISIBLE);
		}
		Button fav = (Button) mCtrls.findViewById(R.id.strip_favorite);
		if(mComicAct.isCurrentFavorite()) {
			fav.setBackgroundDrawable(mComicAct.getResources().getDrawable(R.drawable.star_fav));
		}
		else {
			fav.setBackgroundDrawable(mComicAct.getResources().getDrawable(R.drawable.star_nonfav));
		}
	}

	/**
	 * Hide the control buttons. used while you are trying to download comic
	 */
	public void hideControls() {
		mCtrls.setVisibility(View.INVISIBLE);
		mHandler.removeCallbacks(mChangeVisibility);
	}

	/**
	 * Main function responsible for handling touch events.
	 * @param v the view generating this callback.
	 * @param me event generating this callback
	 * @return true if the event is consumed, else false
	 */
	public boolean onTouch(View v, MotionEvent me) {
		int action = me.getAction() & MotionEvent.ACTION_MASK;
		if(mVt == null) {
			mVt = VelocityTracker.obtain();
		}
		mVt.addMovement(me);
		if(mCtrls.getVisibility() == View.INVISIBLE) {
			resetChangeVisibility();
		}
		long curr = System.currentTimeMillis();
		switch(action) {
		case MotionEvent.ACTION_DOWN:
			mController.stopFling();
			mPrevX = me.getX();
			mPrevY = me.getY();
			if(mPrevDown <= 0) {
				mPrevDown = curr;
			}
			long del = curr - mPrevDown;
			// if it is already reset, then there's no point doing it again!
			if((del > MIN_DOUBLE_TAP) && (del < MAX_DOUBLE_TAP) && (mMode != MODE_RESET)) {
				mPrevMode = mMode;
				mMode = MODE_RESET;
				mPrevDown = 0;  // since you are done with reset don't wait for another reset
				mComicAct.showResetButton();
				break;
			}
			mPrevDown = curr;
			if(mMode == MODE_ZOOM) {
				mMode = MODE_PAN;
			}
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			mMode = MODE_ZOOM;
			float delx = me.getX(0) - me.getX(1);
			float dely = me.getY(0) - me.getY(1);
			mPrevDist = FloatMath.sqrt((delx * delx) + (dely * dely));
			break;
		case MotionEvent.ACTION_POINTER_UP:
			mMode = MODE_PAN;
			break;
		case MotionEvent.ACTION_UP:
			boolean consumed = onFling(me, v);
			if(consumed) {
				mPrevDown = 0;
			}
			recycle();
			break;
		case MotionEvent.ACTION_MOVE:
			int view_wid = v.getWidth();
			int view_hei = v.getHeight();
			if(mMode == MODE_PAN) {
				float dx = (me.getX() - mPrevX) / view_wid;
				float dy = (me.getY() - mPrevY) / view_hei;
				mController.pan(-dx, -dy);
				mPrevX = me.getX();
				mPrevY = me.getY();
			}
			else if(mMode == MODE_ZOOM) {
				float max_dim_log = (float) ((view_wid > view_hei)? Math.log(view_wid) : Math.log(view_hei));
				float delx1 = me.getX(0) - me.getX(1);
				float dely1 = me.getY(0) - me.getY(1);
				float dist = FloatMath.sqrt((delx1 * delx1) + (dely1 * dely1));
				float del_dist = dist - mPrevDist;
				if(Math.abs(del_dist) > MIN_DIST_ZOOM) {
					float zoom = ((float) Math.log(Math.abs(0.1f * del_dist))) / max_dim_log;
					int z_mode = (del_dist < 0)? MODE_ZOOM_OUT : MODE_ZOOM_IN;
					mController.zoom(zoom, z_mode);
					mPrevDist = dist;
				}
			}
			break;
		default:
			recycle();
			mMode = MODE_RESET;
			mPrevMode = mMode;
			break;
		}
		if(mController.isMinimumZoom() && (mMode == MODE_PAN)) {
			mController.resetState();
			mMode = MODE_RESET;
			mPrevMode = mMode;
		}
		return true;
	}

	/**
	 * Recycle the velocity tracker
	 */
	private void recycle() {
		if(mVt != null) {
			mVt.recycle();
			mVt = null;
		}
	}
}
