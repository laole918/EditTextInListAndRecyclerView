package com.laole918.edittextinlistview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.laole918.edittextinlistview.R;
import com.laole918.edittextinlistview.adapter.RecyclerViewAdapter;
import com.laole918.edittextinlistview.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laole918 on 2016/3/19.
 */
public class EditTextInRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext_in_recyclerview);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        List<User> us = getUs();
        adapter.addAll(us);
        adapter.notifyDataSetChanged();
    }

    private List<User> getUs() {
        List<User> us = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            User u = new User();
            u.setName("我是第" + i + "个");
            us.add(u);
        }
        return us;
    }
}
