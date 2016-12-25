package com.micro.android316.housekeeping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.utils.HttpTools;
import com.micro.android316.housekeeping.utils.LoginMessgae;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Administrator on 2016/12/12.
 */

public class Payment extends Activity {

    private ImageView back;
    private String id,price,type;
    private CheckBox weiXin,zhiFuBao;
    private Button tiJiao;
    private static final String URL="http://139.199.196.199/android/index.php/home/index/changeord?";
    private static final int CODE_ZHI_FU=0;
    private static final int CODE_COUNT_DOWN=1;
    private static final int CODE_PAYD_ERROR=-1;
    private TextView countDown;
    private long time=1800*1000;
    private TextView fuKuan1,fuKuan2;
    private TextView serverType;
    private LayoutInflater inflater;
    private PopupWindow window;
    private LinearLayout Boos;
    public static Payment payment;
    //
    private int position=0;       //指向输入的密码个数，当个数超过6个时自动进行网络请求
    private TextView ts[]=new TextView[6];
    //剩余支付时间： 30:00
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        init();
        back.setOnClickListener(listener);
        weiXin.setOnClickListener(listener);
        zhiFuBao.setOnClickListener(listener);
        tiJiao.setOnClickListener(listener);
        countDown();

    }

    public void init(){
        back= (ImageView) findViewById(R.id.back);
        id=getIntent().getStringExtra("id");
        price=getIntent().getStringExtra("price");
        type=getIntent().getStringExtra("type");
        weiXin= (CheckBox) findViewById(R.id.wei_xin);
        zhiFuBao= (CheckBox) findViewById(R.id.zi_fu_bao);
        tiJiao= (Button) findViewById(R.id.ti_jiao);
        countDown= (TextView) findViewById(R.id.count_down);
        fuKuan1= (TextView) findViewById(R.id.price1);
        fuKuan2= (TextView) findViewById(R.id.price2);
        serverType= (TextView) findViewById(R.id.server_type);
        //订单金额： ￥60.00
        fuKuan1.setText("订单金额： ￥"+price+".00");
        fuKuan2.setText("实付金额： ￥"+price+".00");
        serverType.setText(type);
        inflater=LayoutInflater.from(this);
        Boos= (LinearLayout) findViewById(R.id.boos);
        payment=this;
    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.back:
                    finish();
                    break;
                case R.id.wei_xin:
                    weiXin.setChecked(true);
                    zhiFuBao.setChecked(false);
                    break;
                case R.id.zi_fu_bao:
                    weiXin.setChecked(false);
                    zhiFuBao.setChecked(true);
                    break;
                case R.id.ti_jiao:
                    //tiJiao();
                    if(weiXin.isChecked()){
                        Intent intent=new Intent(Payment.this,WeiXin.class);
                        startActivity(intent);
                        return;
                    }
                    Boos.setBackgroundColor(getResources().getColor(R.color.zw_an));
                    pop(tiJiao);
                    break;
            }
        }
    };

    public void tiJiao(){

        String value="id="+id+"&state=1";

        HttpTools tools=new HttpTools(URL+value) {
            @Override
            public void post(String line) {
                try {
                    JSONObject object=new JSONObject(line);
                    if(object.getString("code").equals("1")){
                        handler.sendEmptyMessage(CODE_ZHI_FU);
                    }else {
                        handler.sendEmptyMessage(CODE_PAYD_ERROR);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        tools.runForGet();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CODE_ZHI_FU:
                    Toast.makeText(Payment.this,"支付成功...即将返回主页面",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Payment.this,HomeMainActivity.class);
                    startActivity(intent);
                    break;
                case CODE_COUNT_DOWN:
                    SimpleDateFormat format=new SimpleDateFormat("mm:ss");
                    countDown.setText("剩余支付时间： "+format.format(new Date(time)));
                    if(time<=0){
                        finish();
                    }
                    break;
                case CODE_PAYD_ERROR:
                    Toast.makeText(Payment.this,"支付失败",Toast.LENGTH_SHORT).show();
                    break;

            }

        }
    };

    public void countDown(){
        new Thread(){
            public void run(){
                while(time>=0){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(CODE_COUNT_DOWN);
                    time-=1000;
                }

            }
        }.start();
    }

    public void pop(Button button){

        View view=inflater.inflate(R.layout.zi_fu_bao,null);
        ImageView dis= (ImageView) view.findViewById(R.id.destory);
        final Button pay= (Button) view.findViewById(R.id.pay);
        TextView tx= (TextView) view.findViewById(R.id.type);
        tx.setText(type);
        tx= (TextView) view.findViewById(R.id.price);
        tx.setText(price);
        tx= (TextView) view.findViewById(R.id.zhang_hao);
        tx.setText(LoginMessgae.getTel(this));
        window=new PopupWindow(view);
        window.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        window.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        window.showAsDropDown(button,0,-480);
        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boos.setBackgroundColor(getResources().getColor(R.color.zw_ming));
                window.dismiss();
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v=inflater.inflate(R.layout.zi_fu,null);
                position=0;
                Button button[]=new Button[11];
                int is[]={R.id.txt1,R.id.txt2,R.id.txt3,R.id.txt4,R.id.txt5,R.id.txt6};
                int bs[]={R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,
                        R.id.button7,R.id.button8,R.id.button9,R.id.button0,R.id.button_x};
                for(int i=0;i<6;i++){
                    ts[i]= (TextView) v.findViewById(is[i]);
                }
                for(int i=0;i<11;i++){
                    button[i]= (Button) v.findViewById(bs[i]);
                    if(i<10){
                        button[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String s= (String) (((Button)v).getText());
                                if(position<6){
                                    Log.i("hhhh",position+"");
                                    ts[position++].setText(s);

                                }
                                if(position==6){
                                    tiJiao();
                                    window.dismiss();
                                    Boos.setBackgroundColor(getResources().getColor(R.color.zw_ming));
                                }
                            }
                        });

                    }else {
                        button[i].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(position>0) {
                                    ts[--position].setText("");
                                }
                            }
                        });

                    }

                }
                window.dismiss();
                window=new PopupWindow(v);
                window.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                window.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                window.showAsDropDown(pay,0,250);
            }
        });



    }





}
