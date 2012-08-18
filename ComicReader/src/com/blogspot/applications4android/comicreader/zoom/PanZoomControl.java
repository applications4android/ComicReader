package com.blogspot.applications4android.comicreader.zoom;

import android.graphics.PointF;
import android.os.Handler;
import android.os.SystemClock;


/**
 * Main class for controlling the physics of the pan-zoom-view
 */
public class PanZoomControl {
	/** minimum possible zoom */
	public static final float MIN_ZOOM = 1f;
	/** maximum possible zoom */
	public static final float MAX_ZOOM = 5f;
	/** friction factor for the dynamics */
	public static final float FRICTION_FACTOR = 2.5f;
	/** damping ratio of the spring */
	public static final float DAMPING_RATIO = 10f;
	/** stiffness for dynamics */
	public static final float STIFFNESS = 50f;
    /** target frames per second for snap */
    private static final long FPS = 50;
    /** velocity tolerance for determining 'is-at-rest' */
    private static final float VEL_TOL = 0.04f;
    /** position tolerance for determining 'is-at-rest' */
    private static final float POS_TOL = 0.01f;

	/** friction spring dynamics along x-axis */
	private final FrictionSpringDynamix mPanx = new FrictionSpringDynamix();
	/** friction spring dynamics along y-axis */
	private final FrictionSpringDynamix mPany = new FrictionSpringDynamix();
	/** zoom view to control */
	private PanZoomView mView;
	/** min possible pan */
	private final PointF mMinPan = new PointF();
	/** max possible pan */
	private final PointF mMaxPan = new PointF();
	/** responsible for the dynamics calculation */
	private final Handler mHandler = new Handler();


	/**
	 * Constructor
	 */
	public PanZoomControl(PanZoomView pzv) {
		mView = pzv;
		mPanx.setParameters(FRICTION_FACTOR, STIFFNESS, DAMPING_RATIO);
		mPany.setParameters(FRICTION_FACTOR, STIFFNESS, DAMPING_RATIO);
	}

	/**
	 * Update the state of the controller
	 */
	public void updateState() {
		limitZoom();
		updatePanLimits();
		mView.invalidate();
	}

	/**
	 * Resets the view to the original state
	 */
	public void resetState() {
		mView.setPanXY(0f, 0f);
		mView.setZoom(1f);
		mView.invalidate();
	}

	/**
	 * Checks whether the current zoom is minimum or not
	 * @return true if it is minimum, else false
	 */
	public boolean isMinimumZoom() {
		return (mView.getZoom() <= MIN_ZOOM);
	}

	/**
     * Limits the zoom within the bounds
     */
    private void limitZoom() {
    	if(mView.getZoom() < MIN_ZOOM) {
    		mView.setZoom(MIN_ZOOM);
    	}
    	else if(mView.getZoom() > MAX_ZOOM) {
    		mView.setZoom(MAX_ZOOM);
    	}
    }

	/**
     * Updates the min/max pan
     */
    private void updatePanLimits() {
    	mMinPan.x = 0;
    	mMinPan.y = 0;
    	int im = mView.getImageWidth();
    	int v = mView.getWidth();
    	if((im <= 0) || (v <= 0)) {
    		return;
    	}
    	float zoom = mView.getZoomX() * v / im;
    	mMaxPan.x = (im - (v / zoom)) / im;
    	if((mMaxPan.x <= 0f)) {
    		mMaxPan.x = 0f;
    	}
    	im = mView.getImageHeight();
    	v = mView.getHeight();
    	zoom = mView.getZoomY() * v / im;
    	mMaxPan.y = (im - (v / zoom)) / im;
    	if(mMaxPan.y < 0f) {
    		mMaxPan.y = 0f;
    	}
    }

	/**
	 * Apply zoom onto the image
	 * @param z zoom-factor to apply
	 * @param mode zoom-in or zoom-out?
	 */
	public void zoom(float z, int z_mode) {
		if(z_mode == PanZoomListener.MODE_ZOOM_IN) {
			mView.setZoom(mView.getZoom() + z);
		}
		else if(z_mode == PanZoomListener.MODE_ZOOM_OUT) {
			mView.setZoom(mView.getZoom() - z);
		}
		else {
			return;
		}
		limitZoom();
		mView.invalidate();
	}

	/**
	 * Pan the current image
	 * @param dx pan-amount along X-axis
	 * @param dy pan-amount along Y-axis
	 */
	public void pan(float dx, float dy) {
		dx /= mView.getZoomX();
		dy /= mView.getZoomY();
		updatePanLimits();
		float panx = mView.getPanX();
		float pany = mView.getPanY();
		panx += dx;
		pany += dy;
		if(panx > mMaxPan.x) {
			panx = mMaxPan.x;
		}
		else if(panx < mMinPan.x) {
			panx = mMinPan.x;
		}
		if(pany > mMaxPan.y) {
			pany = mMaxPan.y;
		}
		else if(pany < mMinPan.y) {
			pany = mMinPan.y;
		}
		mView.setPanXY(panx, pany);
		mView.invalidate();
	}

	/**
	 * Starts the fling action
	 * @param velx velocity along X-axis
	 * @param vely velocity along Y-axis
	 */
	public void startFling(float velx, float vely) {
		mPanx.setState(velx / mView.getZoomX(), mView.getPanX());
		mPany.setState(vely / mView.getZoomY(), mView.getPanY());
		updatePanLimits();
		mPanx.setMinMax(mMinPan.x, mMaxPan.x);
		mPany.setMinMax(mMinPan.y, mMaxPan.y);
		mHandler.post(m_fling_runnable);
	}

	/**
	 * Stops the previously started fling action
	 */
	public void stopFling() {
		mHandler.removeCallbacks(m_fling_runnable);
	}

	/** thread responsible for handling all the dynamics calculation */
	private final Runnable m_fling_runnable = new Runnable() {
		public void run() {
			long start = SystemClock.uptimeMillis();
			boolean atRest = true;
			if(!mPanx.isUnderRest(VEL_TOL, POS_TOL)) {
				mPanx.update();
				atRest = false;
			}
			if(!mPany.isUnderRest(VEL_TOL, POS_TOL)) {
				mPany.update();
				atRest = false;
			}
			if(!atRest) {
				mView.setPanXY(mPanx.getPosition(), mPany.getPosition());
				long stop = SystemClock.uptimeMillis();
				mHandler.postDelayed(m_fling_runnable, ((1000 / FPS) - (stop - start)));
				mView.invalidate();
			}
		}
	};
}
