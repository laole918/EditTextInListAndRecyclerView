package com.laole918.edittextinlistview.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
    private int selectPosition = -1;

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

    public void onBindViewHolder(final ListViewHolder holder, final int position) {
        if (holder.editText.getTag() instanceof TextWatcher) {
            holder.editText.removeTextChangedListener((TextWatcher) (holder.editText.getTag()));
        }
        final User u = us.get(position);
        holder.textView.setText(u.getName());
        String phone = u.getPhone();
        if(TextUtils.isEmpty(phone)) {
            holder.editText.setText("");
        } else {
            holder.editText.setText(phone);
        }
        if(selectPosition == position) {
            if(!holder.editText.isFocused()) {
                holder.editText.requestFocus();
            }
            CharSequence text = holder.editText.getText();
            holder.editText.setSelection(TextUtils.isEmpty(text) ? 0 : text.length());
            holder.editText.setCursorVisible(true);
        } else {
            if(holder.editText.isFocused()) {
                holder.editText.clearFocus();
            }
            holder.editText.setCursorVisible(false);
        }
        holder.editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(selectPosition != position && !holder.editText.isFocused()) {
                        holder.editText.requestFocus();
                    }
                    selectPosition = position;
                }
                return false;
            }
        });
        final TextWatcher watcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    u.setPhone(null);
                } else {
                    u.setPhone(String.valueOf(s));
                }
            }
        };
        holder.editText.addTextChangedListener(watcher);
        holder.editText.setTag(watcher);
        holder.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    holder.editText.setCursorVisible(true);
                } else {
                    holder.editText.setCursorVisible(false);
                }
            }
        });
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
