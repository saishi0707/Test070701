package com.example.memoapp.Impl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.memoapp.R;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    static class ViewHolder {
        TextView textView;
        TextView imageView;
    }

    private final LayoutInflater inflater;
    private final int itemLayoutId;
    private final List<String> dates;
    private final List<String> titles;

    public ListViewAdapter(Context context, int itemLayoutId,
                           List<String> scenes, List<String> photos) {
        super();
        this.inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemLayoutId = itemLayoutId;
        this.dates = scenes;
        this.titles = photos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            convertView = inflater.inflate(itemLayoutId, parent, false);

            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.text_title);
            holder.imageView = convertView.findViewById(R.id.text_memo);
            convertView.setTag(holder);
        }
        // holder を使って再利用
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView.setText(titles.get(position));
        holder.textView.setText(dates.get(position));
        return convertView;
    }

    @Override
    public int getCount() {
        // texts 配列の要素数
        return dates.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
