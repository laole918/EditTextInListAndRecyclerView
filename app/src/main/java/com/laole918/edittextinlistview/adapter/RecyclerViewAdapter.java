package com.laole918.edittextinlistview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.laole918.edittextinlistview.R;
import com.laole918.edittextinlistview.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laole918 on 2016/3/19.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private Context mContext;
    private List<User> us = new ArrayList<>();

    public RecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    public void addAll(List<User> us) {
        this.us.addAll(us);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder holder = new RecyclerViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.layout_item_view, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return us.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private EditText editText;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            editText = (EditText) itemView.findViewById(R.id.editText);
        }
    }
}
