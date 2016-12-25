package com.micro.android316.housekeeping.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.micro.android316.housekeeping.CustomControls.HeadImage;
import com.micro.android316.housekeeping.R;
import com.micro.android316.housekeeping.utils.AlbumTools;
import com.micro.android316.housekeeping.utils.HttpPost;
import com.micro.android316.housekeeping.utils.HttpTools;
import com.micro.android316.housekeeping.utils.ImageCat;
import com.micro.android316.housekeeping.utils.LoadImage;
import com.micro.android316.housekeeping.utils.LoginMessgae;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/12/13.
 */
public class PersonalInformationActivity extends Activity{
    private TextView tel,registerTime;
    private EditText birthday,email,personId;
    private static final String URL="http://139.199.196.199/android/index.php/home/index/userinfo?";
    private static final String URI_FOR_CHACGE="http://139.199.196.199/android/index.php/home/index/changeuserinfo?";
    private static final String URL_UPLOAD="http://139.199.196.199/android/index.php/home/index/loadupheadimgae";
    private final int UPLOAD_CODE=1111;
    private final int UPLOAD_CODE_ERROR=2222;
    private HashMap<String,Object> info=new HashMap<>();
    private TextView save;
    private boolean b=true;
    private HeadImage head;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_information);
        init();
        getInfo();

       save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(b){
                   set();
               }
           }
       });

        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlbumTools.openAlbum(PersonalInformationActivity.this,100);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 100:
                if(data==null){
                    return;
                }
                ImageCat.cat(data.getData(),120,120,this,101);
                break;
            case 101:
                if(data==null){
                    return;
                }
                Bitmap bitmap=ImageCat.getBitmap(data);
                head.setBitmap(bitmap);
                try {
                    OutputStream os=openFileOutput("head",MODE_PRIVATE);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, os);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                upload();
                break;
        }
    }

    public void init(){
        tel= (TextView) findViewById(R.id.tel);
        registerTime= (TextView) findViewById(R.id.register_time);
        birthday= (EditText) findViewById(R.id.birthday);
        email= (EditText) findViewById(R.id.email);
        personId= (EditText) findViewById(R.id.person_id);
        tel.setText(LoginMessgae.getTel(this));
        save= (TextView) findViewById(R.id.save);
        head= (HeadImage) findViewById(R.id.personal_head_img);
        back= (ImageView) findViewById(R.id.back);
    }


    public void getInfo(){

        String value="tel="+LoginMessgae.getTel(this)+"&token="+LoginMessgae.getToken(this);

        HttpTools tools=new HttpTools(URL+value) {
            @Override
            public void post(String line) {
                try {
                    JSONObject object=new JSONObject(line);
                    if(object.getString("state").equals("1")){
                        object=object.getJSONObject("info");
                        info.put("mailbox",object.optString("mailbox",""));
                        info.put("id",object.optString("id",""));
                        info.put("createtime",object.getLong("createtime"));
                        info.put("birthday",object.optString("birthday",""));
                        info.put("head",object.optString("head",null));
                        handler.sendEmptyMessage(0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        tools.runForGet();
    }

    private Handler handler=new Handler(){

        public void handleMessage(android.os.Message message){

            if(message.what==1){
                Toast.makeText(PersonalInformationActivity.this,"更改成功",Toast.LENGTH_SHORT).show();
                b=true;
                return;
            }
            if(message.what==-1){
                Toast.makeText(PersonalInformationActivity.this,"更改失败",Toast.LENGTH_SHORT).show();
                b=true;
                return;
            }
            if(message.what==UPLOAD_CODE){
                Toast.makeText(PersonalInformationActivity.this,"头像上传成功",Toast.LENGTH_SHORT).show();
                return;
            }
            if(message.what==UPLOAD_CODE_ERROR){
                Toast.makeText(PersonalInformationActivity.this,"头像上传失败",Toast.LENGTH_SHORT).show();
                return;
            }

            if(!info.get("birthday").equals("null"))
            birthday.setText((String) info.get("birthday"));
            if(!info.get("mailbox").equals("null"))
            email.setText((String) info.get("mailbox"));
            personId.setText((String) info.get("id"));
            long l= (long) info.get("createtime");
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy年MM月dd日");
            registerTime.setText(dateFormat.format(new Date(l)));
            Log.i("hhhhh",info.get("head")+"bigo");
            if(info.get("head")!=null)
            LoadImage.Load(head, (String) info.get("head"),PersonalInformationActivity.this);
        }
    };

    public void set(){
        b=false;
        String value="tel="+LoginMessgae.getTel(this)+"&birthday="+birthday.getText()+"&mailbox="+email.getText()+"&id="+personId.getText();
        Log.i("hhh",URI_FOR_CHACGE+value);
        HttpTools tools=new HttpTools(URI_FOR_CHACGE+value) {
            @Override
            public void post(String line) {
                try {
                    JSONObject object=new JSONObject(line);
                    if(object.getString("code").equals("1")){
                        handler.sendEmptyMessage(1);
                    }else {
                        handler.sendEmptyMessage(-1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        tools.runForGet();
    }

    public void upload(){
       new Thread(){
           public void run(){
               HashMap<String,Object> hashMap=new HashMap<>();
               hashMap.put("tel",LoginMessgae.getTel(PersonalInformationActivity.this));
               HashMap<String,InputStream> hashMap1=new HashMap<>();
               try {
                   hashMap1.put("head",openFileInput("head"));
               } catch (FileNotFoundException e) {
                   e.printStackTrace();
               }
               try {
                   String s= HttpPost.postForIs(URL_UPLOAD,hashMap,hashMap1);
                   JSONObject object=new JSONObject(s);
                   if( object.getString("status").equals("1")){
                       handler.sendEmptyMessage(UPLOAD_CODE);
                   }else {
                       handler.sendEmptyMessage(UPLOAD_CODE_ERROR);
                       Log.i("hhhhh",object.getString("message"));
                   }
               } catch (IOException e) {
                   e.printStackTrace();
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
       }.start();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
