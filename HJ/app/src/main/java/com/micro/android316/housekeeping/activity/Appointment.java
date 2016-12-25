package com.micro.android316.housekeeping.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.utils.GpsUtil;
import com.micro.android316.housekeeping.utils.HttpTools;
import com.micro.android316.housekeeping.utils.LoginMessgae;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/12.
 */

public class Appointment extends Activity {

    private Button yuYue;
    private ImageView back;
    private TextView time,hour,month,day,addHour,subHour,addDay,subDay,addMonth,subMonth,addTime,subTime;
    private static final String URL_ADD_ADDRESS="http://139.199.196.199/android/index.php/home/index/addaddress?";
    private String h,d,t,m,y;
    private String thisM,thisD,thisT;
    private long timeInfo;
    private EditText address;
    private String id,type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment);
        init();
        //getAddress();
        yuYue.setOnClickListener(listener);
        back.setOnClickListener(listener);
        addTime.setOnClickListener(listener);
        subTime.setOnClickListener(listener);
        addHour.setOnClickListener(listener);
        subHour.setOnClickListener(listener);
        addMonth.setOnClickListener(listener);
        subMonth.setOnClickListener(listener);
        addDay.setOnClickListener(listener);
        subDay.setOnClickListener(listener);
        //onCall();
        address.setText("地\t\t址："+LoginMessgae.getAddress(this));

    }

    private void init(){
        yuYue= (Button) findViewById(R.id.li_ji_yu_yue_button);
        back= (ImageView) findViewById(R.id.back);
        time= (TextView) findViewById(R.id.time);
        subTime= (TextView) findViewById(R.id.sub_time);
        addTime= (TextView) findViewById(R.id.add_time);
        hour= (TextView) findViewById(R.id.hour);
        subHour= (TextView) findViewById(R.id.sub_hour);
        addHour= (TextView) findViewById(R.id.add_hour);
        month= (TextView) findViewById(R.id.mouth);
        subMonth= (TextView) findViewById(R.id.sub_month);
        addMonth= (TextView) findViewById(R.id.add_month);
        day= (TextView) findViewById(R.id.day);
        addDay= (TextView) findViewById(R.id.add_day);
        subDay= (TextView) findViewById(R.id.sub_day);
        setT();
        d=day.getText().toString();
        h=hour.getText().toString();
        m=month.getText().toString();
        t=time.getText().toString();
        thisD=d;
        thisM=m;
        thisT=t;
        address= (EditText) findViewById(R.id.address);
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        type=intent.getStringExtra("type");
    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.li_ji_yu_yue_button:
                    Intent intent=new Intent(Appointment.this,OrderConfirm.class);
                    intent.putExtra("address",address.getText().toString());
                    Log.i("hhh",address.getText().toString());
                    intent.putExtra("time",timeInfo);
                    intent.putExtra("long",hour.getText());
                    intent.putExtra("id",id);
                    intent.putExtra("type",type);
                    startActivity(intent);
                    addCommonAddress();
                    break;
                case R.id.back:
                    finish();
                    break;
                case R.id.add_day:
                    addDay();
                    break;
                case R.id.sub_day:
                    subDay();
                    break;
                case R.id.add_month:
                    addMonth();
                    break;
                case R.id.sub_month:
                    subMonth();
                    break;
                case R.id.add_hour:
                    addHour();
                    break;
                case R.id.sub_hour:
                    subHour();
                    break;
                case R.id.add_time:
                    addTime();
                    break;
                case R.id.sub_time:
                    subTime();
                    break;



            }
        }
    };

    public void setT(){
        SimpleDateFormat mat=new SimpleDateFormat("yyyy");
        SimpleDateFormat format=new SimpleDateFormat("MM");
        SimpleDateFormat format1=new SimpleDateFormat("dd");
        SimpleDateFormat format2=new SimpleDateFormat("HH:mm");
        timeInfo=System.currentTimeMillis()+1000*60*60*3;
        time.setText(format2.format(new Date(timeInfo)));
        day.setText(format1.format(new Date(timeInfo)));
        month.setText(format.format(new Date(timeInfo)));
        y=mat.format(new Date(timeInfo));
    }



    public void addHour(){
        char c=h.charAt(0);
        int i=c-48;
        if(i==8){
            return;
        }
        i+=1;
        h=i+"";
        hour.setText(h+"小时");
    }

    public void subHour(){
        char c=h.charAt(0);
        int i=c-48;
        if(i==1){
            return;
        }
        i-=1;
        h=i+"";
        hour.setText(h+"小时");
    }

    public void addMonth(){
        int i=toInt(m);
        if(i==12){
            i=1;
            m=i+"";
            month.setText(m);
            timeInfo+=getSForMonth(i);
            return;
        }
        i+=1;
        m=i+"";
        month.setText(m);
        timeInfo+=getSForMonth(i);


    }

    public void subMonth(){
        int i=toInt(m);
        if(i==toInt(thisM)){

            return;
        }
        if(i==1){
            m=12+"";
            month.setText(m);
            timeInfo-=getSForMonth(i);
            return;
        }
        i-=1;
        m=i+"";
        month.setText(m);
        timeInfo-=getSForMonth(i);
    }
    public void addDay(){
        int d=toInt(this.d);
        int m=toInt(this.m);
        if(isRun()){
            if(m==1||m==3||m==5||m==7||m==8||m==10||m==12){
              if(d==31){
                  day.setText(1+"");
                  timeInfo+=24*3600*1000;
                  return;
              }
            }else if(m==2){
                if(d==29){
                    day.setText(1+"");
                    timeInfo+=24*3600*1000;
                    return;
                }
            }else {
                if(d==31){
                    day.setText(1+"");
                    timeInfo+=24*3600*1000;
                    return;
                }
            }
        }else {
            if(m==1||m==3||m==5||m==7||m==8||m==10||m==12){
                if(d==31){
                    day.setText(1+"");
                    timeInfo+=24*3600*1000;
                    return;
                }
            }else if(m==2){
                if(d==28){
                    day.setText(1+"");
                    timeInfo+=24*3600*1000;
                    return;
                }
            }else {
                if(d==31){
                    day.setText(1+"");
                    timeInfo+=24*3600*1000;
                    return;
                }
            }
        }
        d+=1;
        this.d=d+"";
        day.setText(d+"");
        timeInfo+=24*3600*1000;

    }
    public void subDay(){
        int i=toInt(d);
        if(i==toInt(thisD)){
            return;
        }
        i-=1;
        this.d=i+"";
        day.setText(i+"");
        timeInfo-=24*3600*1000;

    }
    public void addTime(){
        String s=t.split(":")[0];
        int i=toInt(s);
        if(i==18){
            return;
        }
        i+=1;
        t=i+"";
        time.setText(t+":00");
        timeInfo+=3600*1000;


    }
    public void subTime(){
        String s=t.split(":")[0];
        int i=toInt(s);
        if(i==toInt(thisT.split(":")[0])){
            return;
        }
        i-=1;
        t=i+"";
        time.setText(t+":00");
        timeInfo-=3600*1000;
    }

    public int toInt(String s){
        int n=0;
        for(int i=0;i<s.length();i++){
            int nn=s.charAt(i)-48;
            n=n*10+nn;
        }
        return n;
    }

    public long getSForMonth(int m){
        long l=0;
        if(isRun()){
            if(m==1||m==3||m==5||m==7||m==8||m==10||m==12){
                l=31*3600*1000*24;
            }else if(m==2){
                l=29*3600*1000*24;
            }else {
                l=30*3600*1000*24;
            }

        }else {
            if(m==1||m==3||m==5||m==7||m==8||m==10||m==12){
                l=31*3600*1000*24;
            }else if(m==2){
                l=28*3600*1000*24;
            }else {
                l=30*3600*1000*24;
            }
        }

        return l;
    }


    public boolean isRun(){
        int y=toInt(this.y);
        if((y%4==0 && y%100!=0)||y%400==0){
            return  true;
        }else {
            return false;
        }
    }

    public void getAddress(){
        GpsUtil util=new GpsUtil(this,handler);
        util.getAddressInfo();
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message message){
            //Toast.makeText(Appointment.this, (String) message.obj,Toast.LENGTH_LONG).show();
            //地\t\t址：重庆市沙坪坝区小龙坎正街嘉福苑3幢2单元204
            address.setText("地\t\t址："+address((String) message.obj));
        }

    };

    private String address(String json){
        String s="";
        try {
            JSONObject object=new JSONObject(json);
            JSONArray array=object.getJSONArray("result");
            for(int i=1;i<array.length();i++){
                 object=array.getJSONObject(i);
                 String ss=null;
                 ss=object.getString("DistrictName");
                if(ss!=null){
                    s+=ss;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s;


    }


    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;

    public void onCall() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_CALL_PHONE);
                return;
            }
        }else {
            getAddress();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getAddress();
                } else {
                    Toast.makeText(this, "CALL_PHONE Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void addCommonAddress(){
        String value= null;
        try {
            value = "tel="+ LoginMessgae.getTel(this)+"&address="+ URLEncoder.encode(address.getText().toString(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpTools httpTools=new HttpTools(URL_ADD_ADDRESS+value) {
            @Override
            public void post(String line) {

            }
        };
        httpTools.runForGet();

    }





}
