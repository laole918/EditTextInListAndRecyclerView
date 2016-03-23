package com.laole918.edittextinlistview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.laole918.edittextinlistview.R;
import com.laole918.edittextinlistview.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laole918 on 2016/3/19.
 */
public class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<User> us = new ArrayList<>();

    public ListViewAdapter(Context context) {
        this.mContext = context;
    }

    public void addAll(List<User> us) {
        this.us.addAll(us);
    }

    @Override
    public int getCount() {
        return us.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_item_view, parent, false);
            holder = new ListViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ListViewHolder) convertView.getTag();
        }
        onBindViewHolder(holder, position);
        return convertView;
    }

    public void onBindViewHolder(ListViewHolder holder, int position) {
        User u = us.get(position);
        holder.editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {

                } else if(event.getAction() == MotionEvent.ACTION_UP) {

                }
                return false;
            }
        });
        holder.textView.setText(u.getName());
    }

    class ListViewHolder {

        public TextView textView;
        public EditText editText;

        public ListViewHolder(View itemView) {
            textView = (TextView) itemView.findViewById(R.id.textView);
            editText = (EditText) itemView.findViewById(R.id.editText);
        }
    }
}
