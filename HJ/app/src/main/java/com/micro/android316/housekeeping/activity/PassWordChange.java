package com.micro.android316.housekeeping.activity;

import android.app.Activity;
import android.os.*;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.utils.HttpTools;
import com.micro.android316.housekeeping.utils.LoginMessgae;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/12/26.
 */

public class PassWordChange extends Activity {

    private EditText oldPassWord;
    private EditText newPassWord;
    private Button change;
    private final String URL_PASSWORD_CHANGE="http://139.199.196.199/android/index.php/home/index/changepasswd?";
    private final int SUCCESS_CODE=1;
    private final int PASSWD_ERROR=-2;
    private final int ERROR=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_change);
        init();//初始化空间
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void init(){
        oldPassWord= (EditText) findViewById(R.id.old_password);
        newPassWord= (EditText) findViewById(R.id.new_password);
        change= (Button) findViewById(R.id.change);
    }

    private void changePassword(){
        String value="tel="+LoginMessgae.getTel(this)+"&old="+oldPassWord.getText().toString()+"&new="+newPassWord.getText().toString();
        HttpTools tools=new HttpTools(URL_PASSWORD_CHANGE+value) {
            @Override
            public void post(String line) {
                try {
                    JSONObject object=new JSONObject(line);
                    switch ( object.getInt("code")){
                        case SUCCESS_CODE:
                            handler.sendEmptyMessage(SUCCESS_CODE);
                        break;
                        case PASSWD_ERROR:
                            handler.sendEmptyMessage(PASSWD_ERROR);
                        break;
                        case ERROR:
                            handler.sendEmptyMessage(ERROR);
                        break;
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
                case SUCCESS_CODE:
                    Toast.makeText(PassWordChange.this, "更改密码成功", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                    break;
                case PASSWD_ERROR:
                    Toast.makeText(PassWordChange.this, "您输入的密码错误", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    Toast.makeText(PassWordChange.this, "密码更爱失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
