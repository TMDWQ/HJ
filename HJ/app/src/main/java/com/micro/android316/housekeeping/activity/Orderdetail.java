package com.micro.android316.housekeeping.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.utils.HttpTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/12/12.
 */
public class Orderdetail extends Activity {
    private TextView delete,onlinePerson;
    private TextView state,nowTime,serverType,timeLong,totalPrice,paymentAmount,serverTime;
    private TextView serverAddress,number;
    private String code,time,type,price,id,address;
    private  static final int DELETE_SUCESS=1;
    private static  final  int DELETE_FAIL=-1;
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        init();
        setData();
    }
    public void init(){
        state= (TextView) findViewById(R.id.state);
        nowTime= (TextView) findViewById(R.id.now_time);
        serverType= (TextView) findViewById(R.id.server_type);
        timeLong= (TextView) findViewById(R.id.time_long);
        totalPrice= (TextView) findViewById(R.id.total_price);
        paymentAmount= (TextView) findViewById(R.id.payment_amount);
        serverTime= (TextView) findViewById(R.id.server_time);
        serverAddress= (TextView) findViewById(R.id.server_address);
        number= (TextView) findViewById(R.id.number);
        delete=(TextView)findViewById(R.id.textview_delete_order);
        onlinePerson=(TextView)findViewById(R.id.textview_online_person);
        delete.setOnClickListener(onClickListener);
        onlinePerson.setOnClickListener(onClickListener);
        back= (ImageView) findViewById(R.id.img_orderdetail_back);
        back.setOnClickListener(onClickListener);
        getData();
    }
    View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.textview_delete_order:
                    createDialog();
                    break;
                case R.id.textview_online_person:
                    Intent intent=new Intent(Orderdetail.this,Online.class);
                    startActivity(intent);
                    break;
                case R.id.img_orderdetail_back:
                    finish();
                    break;

            }
        }
    };
    public void createDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("您确认要删除订单吗？")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteOrd();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Orderdetail.this,"您的订单已保留",Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }



    public void getData(){
        Intent intent=getIntent();
        code=intent.getStringExtra("state");
        type=intent.getStringExtra("type");
        id=intent.getStringExtra("id");
        price=intent.getStringExtra("price");
        address=intent.getStringExtra("address");
        time=intent.getStringExtra("time");

        //Log.i("twlz",code+","+type+","+id+","+price+","+address);

    }

    public void setData(){
        if(code.equals("0")){
            state.setText("订单未付款");
            SimpleDateFormat format=new SimpleDateFormat("MM-dd HH:mm");
            nowTime.setText(format.format(new Date(System.currentTimeMillis())));
            serverType.setText(type);
            totalPrice.setText("￥"+price);
            paymentAmount.setText("￥0.00");
            serverTime.setText(time);
            serverAddress.setText(address);
            //Log.i("hhh",""+serverAddress+address+"");
            number.setText(id);
            timeLong.setText("*"+toInt(price)/60);
        }else {
            state.setText("订单已付款");
            delete.setText("删除并退款");

            SimpleDateFormat format=new SimpleDateFormat("MM-dd HH:mm");
            nowTime.setText(format.format(new Date(System.currentTimeMillis())));
            serverType.setText(type);
            totalPrice.setText("￥"+price);
            paymentAmount.setText("￥0.00");
            serverTime.setText(time);
            serverAddress.setText(address);
            //Log.i("hhh",""+serverAddress+address+"");
            number.setText(id);
            timeLong.setText("*"+toInt(price)/60);
        }
    }

    public int toInt(String s){
        int number=0;
        for(int i=0;i<s.length();i++){
            char c=s.charAt(i);
            if(c>='0'&&c<='9'){
                int cc=c-48;
                number+=number*10+cc;
            }
        }
        return number;
    }


    public void deleteOrd(){
        String url="http://139.199.196.199/android/index.php/home/index/deleteord?id="+id;
        HttpTools tools=new HttpTools(url) {
            @Override
            public void post(String line) {

                try {
                    JSONObject object=new JSONObject(line);
                    if(object.getString("code").equals("1")){
                       handler.sendEmptyMessage(DELETE_SUCESS);
                    }else {
                        handler.sendEmptyMessage(DELETE_FAIL);
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
        public void handleMessage(android.os.Message message){
           switch (message.what){
               case DELETE_SUCESS:
                   Toast.makeText(Orderdetail.this,"您的订单已删除",Toast.LENGTH_SHORT).show();
                   state.setText("订单已经取消");
                   break;
               case DELETE_FAIL:
                   Toast.makeText(Orderdetail.this,"您的订单取消失败",Toast.LENGTH_SHORT).show();
                   break;
           }
        }
    };
}
