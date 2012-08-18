package com.blogspot.applications4android.comicreader.zoom;

import java.util.Calendar;

/**
 * Uses friction and spring physics in order to provide a more realistic UI.
 * Logic is that when inside the bounds, we'll use friction factor. But as soon
 * as we go out of bounds, we'll apply spring physics.
 */
public class FrictionSpringDynamix {
    /** max time between 2 updates (in ms) */
    private static final int MAX_DELTA_TIME = 25;

    /** amount of friction */
    private float mFric;
    /** amount of spring stiffness */
    private float mStiff;
    /** spring damping ratio */
    private float mDamp;
    /** Current velocity */
    private float mVel;
    /** current position */
    private float mPos;
    /** the last time this object was updated */
    private long mLastUpdate = 0;
    /** current max position */
    private float mMaxPos = Float.MAX_VALUE;
    /** current min position */
    private float mMinPos = -Float.MAX_VALUE;


    /**
     * Set the current state of this object.
     * @param vel current velocity
     * @param pos current position
     * @param time current time
     */
    public void setState(float vel, float pos, long time) {
        mVel = vel;
        mPos = pos;
        mLastUpdate = time;
    }

    /**
     * Set the current state of this object. (time is assumed to be 'now')
     * @param vel current velocity
     * @param pos current position
     */
    public void setState(float vel, float pos) {
        setState(vel, pos, Calendar.getInstance().getTimeInMillis());
    }

    /**
     * Set bounds for this object.
     * @param min the minimum possible value for position.
     * @param max the maximum possible value for position.
     */
    public void setMinMax(float min, float max) {
        mMinPos = min;
        mMaxPos = max;
    }

    /**
     * Get current velocity
     * @return current velocity
     */
    public float getVelocity() {
        return mVel;
    }

    /**
     * Get current position
     * @return current position
     */
    public float getPosition() {
        return mPos;
    }

    /**
     * Finds out whether the object is at rest and is inside the bounds.
     * @param vel_tol velocity tolerance. If current velocity is less than this tolerance,
     *                then it is considered to be at rest.
     * @param pos_tol position tolerance. tolerance to be added to the bounds to check for limits.
     */
    public boolean isUnderRest(float vel_tol, float pos_tol) {
        final boolean rest   = (Math.abs(mVel) <= vel_tol);
        final boolean limits = (mPos <= (mMaxPos + pos_tol)) && (mPos >= (mMinPos - pos_tol));
        return rest && limits;
    }

    /**
     * Interface function responsible for evaluation of the current velocity and position.
     * @param time current time.
     */
    public void update(long time) {
        int delta = (int) (time - mLastUpdate);
        if(delta >= MAX_DELTA_TIME) {
            delta = MAX_DELTA_TIME;
        }
        updateState(delta);
        mLastUpdate = time;
    }

    /**
     * Interface function responsible for evaluation of the current velocity and position.
     * Assumes the time to be 'now'.
     */
    public void update() {
        update(System.currentTimeMillis());
    }

    /**
     * Set the parameters for this object
     * @param fric friction co-efficient
     * @param stiff stiffness of the string
     * @param damping ratio
     */
    public void setParameters(float fric, float stiff, float damp) {
        mFric  = fric;
        mStiff = stiff;
        mDamp = damp;
    }

    /**
     * The base function responsible for actual evaluation of velocity and position for the
     * current time step.
     * @param delta the delta time elapsed from the last update.
     */
    protected void updateState(long delta) {
    	// calculate the acceleration
    	float acc;
    	// has moved way too +ve!
        if(mPos > mMaxPos) {
        	acc = (mMaxPos - mPos) * mStiff;
        	acc = ((mMaxPos - mPos) * mStiff) - (mDamp * mVel);
        	mVel = 0f;
        	acc = 0f;
        	mPos = mMaxPos;
        }
        // has moved way too -ve!
        else if(mPos < mMinPos) {
        	acc = (mMinPos - mPos) * mStiff;
        	acc = ((mMinPos - mPos) * mStiff) - (mDamp * mVel);
        	mVel = 0f;
        	acc = 0f;
        	mPos = mMinPos;
        }
        // within the range
        else {
        	acc = -(mFric * mVel);
        }
        float del = delta / 1000.0f;
        mPos += (mVel * del);
        mVel += (acc * del);
    }
}
