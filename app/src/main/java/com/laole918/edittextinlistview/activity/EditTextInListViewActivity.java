package com.laole918.edittextinlistview.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.laole918.edittextinlistview.R;
import com.laole918.edittextinlistview.adapter.ListViewAdapter;
import com.laole918.edittextinlistview.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laole918 on 2016/3/19.
 */
public class EditTextInListViewActivity extends AppCompatActivity {
    private ListView listView;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext_in_listview);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ListViewAdapter(this);
        listView.setAdapter(adapter);

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
