package com.hoverdroids.sync.listviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class SynchronizedListView extends ListView implements SyncTouchView{

    private boolean isSyncTouchSource = true;

    private OnSyncTouchEventListener onSyncTouchEventListener;

    private boolean isSyncTouchEvent;

    public SynchronizedListView(Context context) {
        super(context);
    }

    public SynchronizedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SynchronizedListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SynchronizedListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //Only relay touch events that are actually on this view
        if (onSyncTouchEventListener != null && isSyncTouchSource && !isSyncTouchEvent){
            onSyncTouchEventListener.onSyncTouchEvent(this, ev);
        }

        //Handle the touch event as though the user made it on this view
        final boolean consumed = super.onTouchEvent(ev);

        //If sync touch event then always return false and allow the initial view to handle
        //everything as usual.
        return !isSyncTouchEvent && consumed;
    }

    @Override
    public void onTouchEvent(View sourceView, MotionEvent ev) {
        if (sourceView.equals(this))
        {
            return;
        }
        isSyncTouchEvent = true;
        onTouchEvent(ev);
        isSyncTouchEvent = false;
    }

    /**
     * Gets onSyncTouchEventListener.
     * @return The onSyncTouchEventListener.
     */
    public OnSyncTouchEventListener getOnSyncTouchEventListener() {
        return onSyncTouchEventListener;
    }

    /**
     * Sets onSyncTouchEventListener.
     * @param onSyncTouchEventListener The onSyncTouchEventListener.
     */
    public void setOnSyncTouchEventListener(OnSyncTouchEventListener onSyncTouchEventListener) {
        this.onSyncTouchEventListener = onSyncTouchEventListener;
    }

    public boolean isSyncTouchSource() {
        return isSyncTouchSource;
    }

    public void setSyncTouchSource(boolean syncTouchSource) {
        isSyncTouchSource = syncTouchSource;
    }
}
