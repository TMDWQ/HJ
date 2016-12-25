package com.micro.android316.housekeeping.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.utils.HttpTools;
import com.micro.android316.housekeeping.utils.MessageVerification;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/12/13.
 */
public class RegisterActivity extends Activity{


    private EditText tel;
    private EditText passwd;
    private EditText verification;
    private TextView getVerification;
    private Button register;
    private ImageView back;
    private MessageVerification messageVerification;
    private boolean isClick=true,isverification=false;
    private static final int VER_CODE=1234;
    private static final int REGISTER_CODE=1;
    private static final int REGISTER_ERROR=-1;
    private static final int REGET_CODE=2;
    private static final String URL="http://139.199.196.199/android/index.php/home/index/register?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        getVerification.setOnClickListener(listener);
        register.setOnClickListener(listener);
    }

    public void init(){
        tel= (EditText) findViewById(R.id.tel);
        passwd= (EditText) findViewById(R.id.passwd);
        verification= (EditText) findViewById(R.id.verification);
        getVerification= (TextView) findViewById(R.id.get_verification);
        register= (Button) findViewById(R.id.register);
        back= (ImageView) findViewById(R.id.back);
        messageVerification=new MessageVerification(this,handler);
    }







    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.register:
                        messageVerification.commint(verification.getText().toString());
                    break;
                case R.id.get_verification:
                    if(!isClick){
                        return;
                    }
                    if(isphone(tel.getText().toString()))
                        messageVerification.send(tel.getText().toString());
                    else
                        Toast.makeText(RegisterActivity.this,"你所输入的不是电话",Toast.LENGTH_SHORT).show();
                    go();
                    isClick=false;
                    break;
            }
        }
    };


    public boolean isphone(String s){
        if(s.length()!=11){
            return false;
        }
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)>'9'||s.charAt(i)<'0'){
                return false;
            }
        }
        return true;
    }

    Handler handler=new Handler(){


        public void handleMessage(Message message){
            switch (message.what){
                case VER_CODE:
                    int event=message.arg1;
                    int result=message.arg2;
                    Object data=message.obj;
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //回调完成
                        Log.i("hhh","----------------------回调完成");

                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            //提交验证码成功
                            Log.i("hhh","----------------------提交验证码成功");
                            register();

                        }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                            //获取验证码成功
                            Log.i("hhh","----------------------获取验证码成功");

                        }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                            //返回支持发送验证码的国家列表
                            Log.i("hhh","----------------------返回支持发送验证码的国家列表");
                        }
                    }else{
                        ((Throwable)data).printStackTrace();
                        Toast.makeText(RegisterActivity.this,"验证码验证失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case REGISTER_CODE:
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case REGISTER_ERROR:
                    Toast.makeText(RegisterActivity.this,"该手机用户已经注册",Toast.LENGTH_SHORT).show();
                    break;
                case REGET_CODE:
                    int i=message.arg1;
                    if(i==0){
                        getVerification.setText("获取验证码");
                        isClick=true;
                        return;
                    }
                    getVerification.setText("重新获取验证码"+i);
                    break;

            }
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        messageVerification.cancellation();
    }

    public void register(){

        String value="tel=" +tel.getText().toString()+ "&passwd="+passwd.getText().toString();
        HttpTools tools=new HttpTools(URL+value) {
            @Override
            public void post(String line) {
                try {
                    JSONObject object=new JSONObject(line);
                    if(object.getString("state").equals("1")){
                        handler.sendEmptyMessage(REGISTER_CODE);
                    }else if(object.getString("state").equals("-1")){
                        handler.sendEmptyMessage(REGISTER_ERROR);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        tools.runForGet();


    }


    public void go(){
        new Thread(){
            @Override
            public void run(){
                for(int i=60;i>=0;i--){
                    Message message=Message.obtain();
                    message.what=REGET_CODE;
                    message.arg1=i;
                    handler.sendMessage(message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


}
