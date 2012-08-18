package com.blogspot.applications4android.comicreader.zoom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


/**
 * Main class responsible for holding a bitmap in order to zoom and pan on it.
 * It's the responsibility of the PanZoomControl to call 'invalidate' when there's
 * something to be updated on the view!
 */
public class PanZoomView extends View {
	/** the bitmap used for drawing the image */
	private Bitmap mMap;
	/** paint used to draw the above bitmap */
	private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
	/** source rectangle */
	private final Rect mSrcRect = new Rect();
	/** destination rectangle */
	private final Rect mDestRect = new Rect();

	/** Pan along the X-axis */
    private float mPanx;
    /** Pan along the Y-axis */
    private float mPany;
    /** Zoom along the X-axis */
    private float mZoomx;
    /** Zoom along the Y-axis */
    private float mZoomy;
    /** Level of zoom. zoom=1 implies the image fits the view in atleast one axis. */
    private float mZoom;
    /** Ratio of the aspectRatio of the image to the aspectRatio of the screen. */
    private float mRatio;

    /** pan zoom controller */
    private PanZoomControl mController;


    /**
	 * Constructor. Just calls the parent class constructor
	 * @param context context of the activity.
	 * @param attrs attributes for this view.
	 */
	public PanZoomView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * Give the controller for pan/zoom
	 * @return the desired controller
	 */
	public PanZoomControl getController() {
		return mController;
	}

	/**
	 * Set the controller for this view
	 * @param mc controller
	 */
	public void setController(PanZoomControl mc) {
		mController = mc;
	}

	/**
	 * bitmap which needs to be zoomed and/or panned.
	 * @param bm the bitmap of interest.
	 */
	public void setImageBitmap(Bitmap bm) {
		if(mMap != null) {
			mMap.recycle();
		}
		mMap = bm;
		if(bm == null) {
			return;
		}
		setAspectRatio(getWidth(), getHeight(), mMap.getWidth(), mMap.getHeight());
	}

	/**
	 * current bitmap
	 * @return the bitmap of interest.
	 */
	public Bitmap getImageBitmap() {
		return mMap;
	}

	/**
	 * How to draw this view?
	 * @param canvas canvas where to draw
	 */
    @Override
    protected void onDraw(Canvas canvas) {
    	if(mMap == null) {
    		return;
    	}
    	int viewWidth = getWidth();
        int viewHeight = getHeight();
        int imWidth = mMap.getWidth();
        int imHeight = mMap.getHeight();
    	float zoomx = mZoomx * viewWidth / imWidth;
    	float zoomy = mZoomy * viewHeight / imHeight;
    	mDestRect.left = getLeft();
    	mDestRect.top = getTop();
    	mDestRect.right = getRight();
    	mDestRect.bottom = getBottom();
    	mSrcRect.left = (int) (mPanx * imWidth);
    	mSrcRect.top = (int) (mPany * imHeight);
    	mSrcRect.right = (int) (mSrcRect.left + (viewWidth / zoomx));
    	mSrcRect.bottom = (int) (mSrcRect.top + (viewHeight / zoomy));
    	if(mSrcRect.left < 0) {
    		mDestRect.left += (-mSrcRect.left * zoomx);
    		mSrcRect.left = 0;
    	}
    	if(mSrcRect.right > imWidth) {
    		mDestRect.right -= ((mSrcRect.right - imWidth) * zoomx);
    		mSrcRect.right = imWidth;
    	}
    	if(mSrcRect.top < 0) {
    		mDestRect.top += (-mSrcRect.top * zoomy);
    		mSrcRect.top = 0;
    	}
    	if(mSrcRect.bottom > imHeight) {
    		mDestRect.bottom -= ((mSrcRect.bottom - imHeight) * zoomy);
    		mSrcRect.bottom = imHeight;
    	}
        canvas.drawBitmap(mMap, mSrcRect, mDestRect, mPaint);
        // This is bad! If you call 'invalidate', then the 'onDraw' function will again be called!
        // Leading to an infinite loop!
        //invalidate();
    }

    /**
	 * updates the aspect ratio in event of a layout change.
	 * @param changed whether the orientation changed or not
	 * @param left leftmost part of the rectangle
	 * @param top topmost part of the rectangle
	 * @param right rightmost part of the rectangle
	 * @param bottom bottom-most part of the rectangle
	 */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(mMap != null) {
        	setAspectRatio(right - left, bottom - top, mMap.getWidth(), mMap.getHeight());
        }
    }

    /**
     * Get the image width (in pixels)
     * @return image width
     */
    public int getImageWidth() {
    	if(mMap == null) {
    		return 0;
    	}
    	return mMap.getWidth();
    }

    /**
     * Get the image height (in pixels)
     * @return image height
     */
    public int getImageHeight() {
    	if(mMap == null) {
    		return 0;
    	}
    	return mMap.getHeight();
    }

    /**
     * Get the current pan-x value.
     * @return pan-x value
     */
    public float getPanX() {
        return mPanx;
    }

    /**
     * Get the current pan-y value
     * @return pan-y value
     */
    public float getPanY() {
        return mPany;
    }

    /**
     * Get the current value of zoom along X-axis.
     * @return zoom-x value
     */
    public float getZoomX() {
        return mZoomx;
    }

    /**
     * Get the current value of zoom along Y-axis.
     * @return zoom-y value
     */
    public float getZoomY() {
        return mZoomy;
    }

    /**
     * Get the current zoom level.
     * @return zoom level
     */
    public float getZoom() {
        return mZoom;
    }

    /**
     * Get the current value of aspect ratio.
     * @return aspect ratio
     */
    public float getAspectRatio() {
        return mRatio;
    }

    /**
     * Set the value of pan along X-axis and Y-axis.
     * @param panx value of pan along X-axis.
     * @param pany value of pan along Y-axis.
     */
    public void setPanXY(float panx, float pany) {
    	mPanx = panx;
    	mPany = pany;
    }

    /**
     * Set the value of zoom level.
     * @param zoom value of zoom level.
     */
    public void setZoom(float zoom) {
        if(zoom != mZoom) {
            mZoom = zoom;
            setZoomXY();
        }
    }

    /**
     * Evaluate the value of ratio of aspect-ratios.
     * @param view_wid Width of the view (in pixels)
     * @param view_hei Height of the view (in pixels)
     * @param im_wid Width of the image (in pixels)
     * @param im_hei Height of the image (in pixels)
     * @return the ratio of aspect-ratios.
     */
    private void setAspectRatio(int view_wid, int view_hei, int im_wid, int im_hei) {
    	if((view_wid <= 0) || (view_hei <= 0) || (im_wid <= 0) || (im_hei <= 0)) {
    		return;
    	}
    	float view_ratio = ((float) view_wid) / ((float) view_hei);
        float imRatio   = ((float) im_wid) / ((float) im_hei);
        float ratio = imRatio / view_ratio;
        if(ratio != mRatio) {
            mRatio = ratio;
            setZoomXY();
    		mController.updateState();
        }
    }

    /**
     * Helper function to evaluate the zoom-x and zoom-y values from
     * the current value of zoom and aspect-ratio. Function calling this is
     * responsible for calling 'invalidate()' in order to communicate the
     * changes.
     */
    private void setZoomXY() {
        if(mRatio != 0.0f) {
            mZoomx = Math.min(mZoom, mZoom * mRatio);
            mZoomy = Math.min(mZoom, mZoom / mRatio);
        }
    }

}
