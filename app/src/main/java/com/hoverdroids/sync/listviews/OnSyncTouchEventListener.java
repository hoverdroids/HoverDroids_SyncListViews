package com.hoverdroids.sync.listviews;

import android.view.MotionEvent;
import android.view.View;

public interface OnSyncTouchEventListener {

    void onSyncTouchEvent(View sourceView, MotionEvent ev);
}
