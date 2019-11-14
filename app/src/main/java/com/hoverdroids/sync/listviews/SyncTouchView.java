package com.hoverdroids.sync.listviews;

import android.view.MotionEvent;
import android.view.View;

public interface SyncTouchView {
    void onTouchEvent(View sourceView, MotionEvent ev);
}
