package com.laole918.edittextinlistview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.laole918.edittextinlistview.activity.EditTextInListViewActivity;
import com.laole918.edittextinlistview.activity.EditTextInRecyclerViewActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnList, btnRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnList = (Button) findViewById(R.id.btn_list);
        if(btnList != null) {
            btnList.setOnClickListener(this);
        }
        btnRecycler = (Button) findViewById(R.id.btn_recycler);
        if(btnRecycler != null) {
            btnRecycler.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.equals(btnList)) {
            Intent intent = new Intent();
            intent.setClass(this, EditTextInListViewActivity.class);
            startActivity(intent);
        } else if(v.equals(btnRecycler)) {
            Intent intent = new Intent();
            intent.setClass(this, EditTextInRecyclerViewActivity.class);
            startActivity(intent);
        }
    }
}
