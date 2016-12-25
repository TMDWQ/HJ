package com.micro.android316.housekeeping.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.utils.HttpTools;
import com.micro.android316.housekeeping.utils.LoginMessgae;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/12/16.
 */

public class Login extends Activity{

    private EditText telPhone;
    private EditText passWord;
    private Button login;
    private CheckBox remenber;
    private TextView register;
    private final static String URL="http://139.199.196.199/android/index.php/home/index/login?";
    private final static int LOGIN_SUCCESS=1;
    private final static  int LONGIN_ERROR=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        login();
        remenberPasswd();
        go();
        register();


    }

    private void init(){
        login= (Button) findViewById(R.id.login);
        passWord= (EditText) findViewById(R.id.passwd);
        telPhone= (EditText) findViewById(R.id.tel);
        remenber= (CheckBox) findViewById(R.id.remenber);
        register= (TextView) findViewById(R.id.register);
    }

    public void login(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(telPhone.length()==0 || telPhone.getText()==null){
                    Toast.makeText(Login.this,"电话不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(passWord.length()==0 || passWord.getText()==null){
                    Toast.makeText(Login.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!isphone(telPhone.getText().toString())){
                    Toast.makeText(Login.this,"你输入的不是电话号码",Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(Login.this,"正在登录....",Toast.LENGTH_SHORT).show();
                new Thread(){
                    public void run(){
                        String value="tel="+telPhone.getText()+"&passwd="+passWord.getText();
                        HttpTools httpTools=new HttpTools(URL+value) {
                            @Override
                            public void post(String line) {
                                try {
                                    JSONObject object=new JSONObject(line);
                                    if(object.getString("state").equals("1")){
                                        handler.sendEmptyMessage(LOGIN_SUCCESS);
                                        LoginMessgae.saveToken(Login.this,object.getString("token"));
                                        LoginMessgae.save(Login.this,telPhone.getText().toString(),passWord.getText().toString());
                                    }else {
                                        handler.sendEmptyMessage(LONGIN_ERROR);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        httpTools.runForGet();
                    }
                }.start();
            }
        });
    }

    Handler handler=new Handler(){

        public void handleMessage(Message message){
            switch (message.what){
                case LOGIN_SUCCESS:
                    Intent intent=new Intent(Login.this,HomeMainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case LONGIN_ERROR:
                    Toast.makeText(Login.this,"你的账号或者密码错误",Toast.LENGTH_SHORT).show();
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

    public void register(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void remenberPasswd(){
        Boolean b=LoginMessgae.getSateForRemenberPassWord(this);
        remenber.setChecked(b);
        if(b){
            telPhone.setText(LoginMessgae.getTel(this));
            passWord.setText(LoginMessgae.getpassWord(this));
        }


    }

    public void go(){
        telPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                passWord.setText("");
            }
        });
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginMessgae.saveStateForRemenberPassWord(this,remenber.isChecked());
    }
}
