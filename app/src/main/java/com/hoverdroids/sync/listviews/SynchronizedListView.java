package com.hoverdroids.sync.listviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import static com.hoverdroids.sync.listviews.SourceMode.NOT_SOURCE;

public class SynchronizedListView extends ListView implements SyncTouchView{

    private SourceMode sourceMode = SourceMode.XY;
    private SyncMode syncMode = SyncMode.XY;

    private OnSyncTouchEventListener onSyncTouchEventListener;

    private boolean isSyncTouchEvent;

    public SynchronizedListView(Context context) {
        super(context);
    }

    public SynchronizedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        readCustomAttrs(attrs,0, 0);
    }

    public SynchronizedListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readCustomAttrs(attrs, defStyleAttr, 0);
    }

    public SynchronizedListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        readCustomAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void readCustomAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes){
        if (attrs == null){
            return;
        }

        final Context context = getContext();
        final TypedArray ary = context.obtainStyledAttributes(attrs, R.styleable.SynchronizedListView, defStyleAttr, defStyleRes);

        int mode = ary.getInt(R.styleable.SynchronizedListView_sourceMode, SourceMode.XY.ordinal());
        sourceMode = SourceMode.values()[mode];

        mode = ary.getInt(R.styleable.SynchronizedListView_syncMode, SyncMode.XY.ordinal());
        syncMode = SyncMode.values()[mode];

        ary.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //Only relay touch events that are actually on this view
        if (onSyncTouchEventListener != null && !sourceMode.equals(NOT_SOURCE) && !isSyncTouchEvent){

            //Send touch event data in X, Y, or both. This allows the output to easily be filtered
            //in a single direction - ie avoids the need to determine how much movement in an axis
            //we don't care to sync.
            final MotionEvent clonedEvent = MotionEvent.obtain(ev);

            if (ev.getHistorySize() > 1) {
                final float origX = ev.getHistoricalX(0, 0);
                final float origY = ev.getHistoricalX(0, 0);

                float x = SourceMode.X.equals(sourceMode) || SourceMode.XY.equals(sourceMode) ? ev.getX() : origX;
                float y = SourceMode.Y.equals(sourceMode) || SourceMode.XY.equals(sourceMode) ? ev.getY() : origY;

                clonedEvent.setLocation(x, y);
            }

            onSyncTouchEventListener.onSyncTouchEvent(this, ev);
            clonedEvent.recycle();
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

    public SourceMode getSourceMode() {
        return sourceMode;
    }

    public void setSourceMode(SourceMode sourceMode) {
        this.sourceMode = sourceMode;
    }

    public SyncMode getSyncMode() {
        return syncMode;
    }

    public void setSyncMode(SyncMode syncMode) {
        this.syncMode = syncMode;
    }
}
