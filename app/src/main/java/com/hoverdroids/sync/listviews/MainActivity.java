package com.hoverdroids.sync.listviews;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnSyncTouchEventListener {

    @BindView(R.id.listview_left)
    SynchronizedListView leftListView;

    @BindView(R.id.listview_center)
    SynchronizedListView centerListView;

    @BindView(R.id.listview_right)
    SynchronizedListView rightListView;

    private BaseAdapter leftAdapter;
    private BaseAdapter centerAdapter;
    private BaseAdapter rightAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        leftAdapter = new SimpleAdapter(getApplicationContext(), getItems());
        leftListView.setAdapter(leftAdapter);
        leftListView.setOnSyncTouchEventListener(this);

        centerAdapter = new SimpleAdapter(getApplicationContext(), getItems());
        centerListView.setAdapter(centerAdapter);
        centerListView.setOnSyncTouchEventListener(this);

        rightAdapter = new SimpleAdapter(getApplicationContext(), getItems());
        rightListView.setAdapter(rightAdapter);
        rightListView.setOnSyncTouchEventListener(this);
    }

    @Override
    public void onSyncTouchEvent(View sourceView, MotionEvent ev) {
        leftListView.onTouchEvent(sourceView, ev);
        centerListView.onTouchEvent(sourceView, ev);
        rightListView.onTouchEvent(sourceView, ev);
    }

    private List<SimpleItem> getItems(){
        final Random rnd = new Random();

        final List<SimpleItem> items = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            final int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            items.add(new SimpleItem(color));
        }

        return items;
    }
}
