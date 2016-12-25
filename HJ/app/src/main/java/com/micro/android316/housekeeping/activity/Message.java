package com.micro.android316.housekeeping.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.micro.android316.housekeeping.R;

/**
 * Created by Administrator on 2016/12/16.
 */

public class Message extends Activity {

    private TextView now,old;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        init();
    }
    private void init(){
        now= (TextView) findViewById(R.id.now_message);
        old= (TextView) findViewById(R.id.history_message);
        now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                now.setTextColor(getResources().getColor(R.color.text_blue));
                old.setTextColor(getResources().getColor(R.color.wode_text));
            }
        });

        old.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old.setTextColor(getResources().getColor(R.color.text_blue));
                now.setTextColor(getResources().getColor(R.color.wode_text));

            }
        });
    }
}
