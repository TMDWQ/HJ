package com.micro.android316.housekeeping.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.micro.android316.housekeeping.R;

/**
 * Created by 张文 on 2016/12/24.
 */

public class WeiXin extends Activity {

    private TextView textView[]=new TextView[6];
    private int txtId[]={R.id.txt1,R.id.txt2,R.id.txt3,R.id.txt4,R.id.txt5,R.id.txt6};
    private Button button[]=new Button[11];
    private  int buttonId[]={R.id.button1,R.id.button2,R.id.button3,R.id.button4,
            R.id.button5,R.id.button6,R.id.button7,R.id.button8,R.id.button9,
            R.id.button0,R.id.button_x};
    private int position=0;
    private Button liJiZiFu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wei_xin_zi_fu);
        init();

    }

    private void init(){
        for(int i=0;i<6;i++){
            textView[i]= (TextView) findViewById(txtId[i]);
        }
        for(int i=0;i<10;i++){
            button[i]= (Button) findViewById(buttonId[i]);
            button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position<6) {
                        Button button = (Button) v;
                        textView[position++].setText(button.getText());
                    }
                }
            });
        }
        button[10]= (Button) findViewById(buttonId[10]);
        button[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>0){
                    textView[--position].setText("");
                }
            }
        });
        liJiZiFu= (Button) findViewById(R.id.li_ji_zi_fu);
        liJiZiFu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==6){
                    Payment.payment.tiJiao();
                    finish();
                }
            }
        });
    }


}
