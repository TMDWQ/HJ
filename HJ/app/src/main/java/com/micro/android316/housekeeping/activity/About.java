package com.micro.android316.housekeeping.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.micro.android316.housekeeping.R;

import com.micro.android316.housekeeping.R;

/**
 * Created by Administrator on 2016/12/12.
 */
public class About extends Activity {
    private ImageView back,head;
    private TextView fuction;
    private LinearLayout inform,update;
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.img_about_back:
                    finish();
                    break;
                case R.id.img_about_head:
                    break;
                case R.id.textview_about_fuction:
                    break;
                case R.id.linear_about_inform:
                    break;
                case R.id.linear_about_update:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        init();
    }
    public void init(){
        back =(ImageView)findViewById(R.id.img_about_back);
        head=(ImageView)findViewById(R.id.img_about_head);
        fuction=(TextView)findViewById(R.id.textview_about_fuction);
        inform=(LinearLayout)findViewById(R.id.linear_about_inform);
        update=(LinearLayout)findViewById(R.id.linear_about_update);

        back.setOnClickListener(onClickListener);
        head.setOnClickListener(onClickListener);
        fuction.setOnClickListener(onClickListener);
        inform.setOnClickListener(onClickListener);
        update.setOnClickListener(onClickListener);
    }
}
