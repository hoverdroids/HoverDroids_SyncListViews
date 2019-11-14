package com.hoverdroids.sync.listviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class SimpleAdapter extends BaseAdapter {

    private Context context;

    private List<SimpleItem> items;

    public SimpleAdapter(final Context context, final List<SimpleItem> items){
        super();
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            final LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.simple_adapter_item, parent, false);
        }

        convertView.setBackgroundColor(((SimpleItem)getItem(position)).getColor());

        return convertView;
    }
}