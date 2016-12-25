package com.micro.android316.housekeeping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.utils.HttpTools;
import com.micro.android316.housekeeping.utils.LoginMessgae;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/12.
 */

public class OrderConfirm extends Activity {

    private Button submit;
    private ImageView back;
    private long time;
    private String address;
    private String timeSize;
    private int price;
    private TextView serverTime;
    private TextView serverPrice;
    private TextView serverType;
    private TextView serverAddress;
    private String id,type,orderId;
    private static final String URL="http://139.199.196.199/android/index.php/home/index/ordsubmit?";
    //tel=123456&nanny=1&address=china&time=2016/12/13&len=2:00&price=$108
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_confirm);
        init();
        submit.setOnClickListener(listener);
        back.setOnClickListener(listener);

    }


    public void init(){
        submit= (Button) findViewById(R.id.submit);
        back= (ImageView) findViewById(R.id.back);
        Intent intent=getIntent();
        time=intent.getLongExtra("time",0);
        address=intent.getStringExtra("address");
        timeSize=intent.getStringExtra("long");
        id=intent.getStringExtra("id");
        type=intent.getStringExtra("type");
        int i=timeSize.charAt(0)-48;
        price=i*60;
        serverTime= (TextView) findViewById(R.id.server_time);
        serverPrice= (TextView) findViewById(R.id.server_price);
        //服务时间：10-22（周六）17:30-19:30
        //实际价格：￥60.00
        serverPrice.setText("实际价格：￥"+price+".00");
        SimpleDateFormat format=new SimpleDateFormat("MM-dd");
        SimpleDateFormat format1=new SimpleDateFormat("E");
        SimpleDateFormat format2=new SimpleDateFormat("HH:mm");
        serverTime.setText("服务时间："+format.format(new Date(time))+
                "("+jieXi(format1.format(new Date(time)))+")"+format2.format(new Date(time))+"-"
                +format2.format(new Date(time+i*3600*1000)));
        serverAddress= (TextView) findViewById(R.id.server_address);
        serverType= (TextView) findViewById(R.id.server_type);
        //服务项目：家居清洁
        serverType.setText("服务项目："+type);
        serverAddress.setText(address);

    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.submit:
                    addOrder();
                    break;
                case R.id.back:
                    finish();
                    break;

            }
        }
    };

    //MON、THU、WED、THU、FRI、SAT、SUN
    public String jieXi(String e){
        String es[]={"Mon","The","Wed","Thu","Fri","Sat","Sun"};
        if(e.equals(es[0])){
            return "星期一";
        }
        if(e.equals(es[1])){
            return "星期二";
        }
        if(e.equals(es[2])){
            return "星期三";
        }
        if(e.equals(es[3])){
            return "星期四";
        }
        if(e.equals(es[4])){
            return "星期五";
        }
        if(e.equals(es[5])){
            return "星期六";
        }
        if(e.equals(es[6])){
            return "星期天";
        }
        return e;
    }

    public void addOrder(){
        String url=URL;

        String value= null;
        try {
            value = "tel=" + LoginMessgae.getTel(this)+ "&nanny=" +id+ "&address=" + URLEncoder.encode(address,"utf-8")+ "&time=" +time+ "&len=" +URLEncoder.encode(timeSize,"utf-8")+ "&price="+price;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.i("hhh",url+value);

        HttpTools tools=new HttpTools(url+value) {
            @Override
            public void post(String line) {
                try {
                    JSONObject object=new JSONObject(line);
                    if(object.getString("code").equals("1")){
                        orderId=object.getString("id");
                        handler.sendEmptyMessage(0);
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
        public void handleMessage(Message message){
            Intent intent=new Intent(OrderConfirm.this,Payment.class);
            intent.putExtra("id",orderId);
            intent.putExtra("price",price+"");
            intent.putExtra("type",type);
            startActivity(intent);
        }
    };
}
